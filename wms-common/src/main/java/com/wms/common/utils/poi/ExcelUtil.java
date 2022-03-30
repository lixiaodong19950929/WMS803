package com.wms.common.utils.poi;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.wms.common.annotation.Excel;
import com.wms.common.config.Global;
import com.wms.common.core.text.Convert;
import com.wms.common.utils.StringUtils;
import com.wms.common.annotation.Excels;
import com.wms.common.core.domain.AjaxResult;
import com.wms.common.exception.BusinessException;
import com.wms.common.utils.DateUtils;
import com.wms.common.utils.reflect.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;

@Slf4j
public class ExcelUtil<T>
{
    /**
     * Excel sheet最大行数，默认65536
     */
    public static final int sheetSize = 65536;

    /**
     * 工作表名称
     */
    private String sheetName;

    /**
     * 导出类型（EXPORT:导出数据；IMPORT：导入模板）
     */
    private Excel.Type type;

    /**
     * 工作薄对象
     */
    private Workbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 样式列表
     */
    private Map<String, CellStyle> styles;

    /**
     * 导入导出数据列表
     */
    private List<T> list;

    /**
     * 注解列表
     */
    private List<Object[]> fields;

    /**
     * 实体对象
     */
    public Class<T> clazz;

    // 样式
    private HSSFCellStyle cellStyle;

    public ExcelUtil() {
        super();
    }

    public ExcelUtil(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    public void init(List<T> list, String sheetName, Excel.Type type)
    {
        if (list == null)
        {
            list = new ArrayList<T>();
        }
        this.list = list;
        this.sheetName = sheetName;
        this.type = type;
        createExcelField();
        createWorkbook();
    }

    /**
     * 对excel表单默认第一个索引名转换成list
     *
     * @param is 输入流
     * @return 转换后集合
     */
    public List<T> importExcel(InputStream is) throws Exception
    {
        return importExcel(StringUtils.EMPTY, is);
    }

    /**
     * 对excel表单指定表格索引名转换成list
     *
     * @param sheetName 表格索引名
     * @param is 输入流
     * @return 转换后集合
     */
    public List<T> importExcel(String sheetName, InputStream is) throws Exception
    {
        this.type = Excel.Type.IMPORT;
        this.wb = WorkbookFactory.create(is);
        List<T> list = new ArrayList<T>();
        Sheet sheet = null;
        if (StringUtils.isNotEmpty(sheetName))
        {
            // 如果指定sheet名,则取指定sheet中的内容.
            sheet = wb.getSheet(sheetName);
        }
        else
        {
            // 如果传入的sheet名不存在则默认指向第1个sheet.
            sheet = wb.getSheetAt(0);
        }

        if (sheet == null)
        {
            throw new IOException("文件sheet不存在");
        }

        int rows = sheet.getPhysicalNumberOfRows();

        if (rows > 0)
        {
            // 定义一个map用于存放excel列的序号和field.
            Map<String, Integer> cellMap = new HashMap<String, Integer>();
            // 获取表头
            Row heard = sheet.getRow(0);
            for (int i = 0; i < heard.getPhysicalNumberOfCells(); i++)
            {
                Cell cell = heard.getCell(i);
                if (StringUtils.isNotNull(cell != null))
                {
                    String value = this.getCellValue(heard, i).toString();
                    cellMap.put(value, i);
                }
                else
                {
                    cellMap.put(null, i);
                }
            }
            // 有数据时才处理 得到类的所有field.
            Field[] allFields = clazz.getDeclaredFields();
            // 定义一个map用于存放列的序号和field.
            Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();
            for (int col = 0; col < allFields.length; col++)
            {
                Field field = allFields[col];
                Excel attr = field.getAnnotation(Excel.class);
                if (attr != null && (attr.type() == Excel.Type.ALL || attr.type() == type))
                {
                    // 设置类的私有字段属性可访问.
                    field.setAccessible(true);
                    Integer column = cellMap.get(attr.name());
                    fieldsMap.put(column, field);
                }
            }
            for (int i = 1; i < rows; i++)
            {
                // 从第2行开始取数据,默认第一行是表头.
                Row row = sheet.getRow(i);
                T entity = null;
                for (Map.Entry<Integer, Field> entry : fieldsMap.entrySet())
                {
                    Object val = this.getCellValue(row, entry.getKey());

                    // 如果不存在实例则新建.
                    entity = (entity == null ? clazz.newInstance() : entity);
                    // 从map中得到对应列的field.
                    Field field = fieldsMap.get(entry.getKey());
                    // 取得类型,并根据对象类型设置值.
                    Class<?> fieldType = field.getType();
                    if (String.class == fieldType)
                    {
                        String s = Convert.toStr(val);
                        if (StringUtils.endsWith(s, ".0"))
                        {
                            val = StringUtils.substringBefore(s, ".0");
                        }
                        else
                        {
                            String dateFormat = field.getAnnotation(Excel.class).dateFormat();
                            if (StringUtils.isNotEmpty(dateFormat))
                            {
                                val = DateUtils.parseDateToStr(dateFormat, (Date) val);
                            }
                            else
                            {
                                val = Convert.toStr(val);
                            }                        }
                    }
                    else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType))
                    {
                        val = Convert.toInt(val);
                    }
                    else if ((Long.TYPE == fieldType) || (Long.class == fieldType))
                    {
                        val = Convert.toLong(val);
                    }
                    else if ((Double.TYPE == fieldType) || (Double.class == fieldType))
                    {
                        val = Convert.toDouble(val);
                    }
                    else if ((Float.TYPE == fieldType) || (Float.class == fieldType))
                    {
                        val = Convert.toFloat(val);
                    }
                    else if (BigDecimal.class == fieldType)
                    {
                        val = Convert.toBigDecimal(val);
                    }
                    else if (Date.class == fieldType)
                    {
                        if (val instanceof String)
                        {
                            val = DateUtils.parseDate(val);
                        }
                        else if (val instanceof Double)
                        {
                            val = DateUtil.getJavaDate((Double) val);
                        }
                    }
                    if (StringUtils.isNotNull(fieldType))
                    {
                        Excel attr = field.getAnnotation(Excel.class);
                        String propertyName = field.getName();
                        if (StringUtils.isNotEmpty(attr.targetAttr()))
                        {
                            propertyName = field.getName() + "." + attr.targetAttr();
                        }
                        else if (StringUtils.isNotEmpty(attr.readConverterExp()))
                        {
                            val = reverseByExp(String.valueOf(val), attr.readConverterExp());
                        }
                        ReflectUtils.invokeSetter(entity, propertyName, val);
                    }
                }
                list.add(entity);
            }
        }
        return list;
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param list 导出数据集合
     * @param sheetName 工作表的名称
     * @return 结果
     */
    public AjaxResult exportExcel(List<T> list, String sheetName)
    {
        this.init(list, sheetName, Excel.Type.EXPORT);
        return exportExcel();
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param sheetName 工作表的名称
     * @return 结果
     */
    public AjaxResult importTemplateExcel(String sheetName)
    {
        this.init(null, sheetName, Excel.Type.IMPORT);
        return exportExcel();
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @return 结果
     */
    public AjaxResult exportExcel()
    {
        OutputStream out = null;
        try
        {
            // 取出一共有多少个sheet.
            double sheetNo = Math.ceil(list.size() / sheetSize);
            for (int index = 0; index <= sheetNo; index++)
            {
                createSheet(sheetNo, index);

                // 产生一行
                Row row = sheet.createRow(0);
                int column = 0;
                // 写入各个字段的列头名称
                for (Object[] os : fields)
                {
                    Excel excel = (Excel) os[1];
                    this.createCell(excel, row, column++);
                }
                if (Excel.Type.EXPORT.equals(type))
                {
                    fillExcelData(index, row);
                }
            }
            String filename = encodingFilename(sheetName);
            out = new FileOutputStream(getAbsoluteFile(filename));
            wb.write(out);
            return AjaxResult.success(filename);
        }
        catch (Exception e)
        {
            log.error("导出Excel异常{}", e.getMessage());
            throw new BusinessException("导出Excel失败，请联系网站管理员！");
        }
        finally
        {
            if (wb != null)
            {
                try
                {
                    wb.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 填充excel数据
     *
     * @param index 序号
     * @param row 单元格行
     */
    public void fillExcelData(int index, Row row)
    {
        int startNo = index * sheetSize;
        int endNo = Math.min(startNo + sheetSize, list.size());
        for (int i = startNo; i < endNo; i++)
        {
            row = sheet.createRow(i + 1 - startNo);
            // 得到导出对象.
            T vo = (T) list.get(i);
            int column = 0;
            for (Object[] os : fields)
            {
                Field field = (Field) os[0];
                Excel excel = (Excel) os[1];
                // 设置实体类私有属性可访问
                field.setAccessible(true);
                this.addCell(excel, row, vo, field, column++);
            }
        }
    }

    /**
     * 创建表格样式
     *
     * @param wb 工作薄对象
     * @return 样式列表
     */
    private Map<String, CellStyle> createStyles(Workbook wb)
    {
        // 写入各条记录,每条记录对应excel表中的一行
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        styles.put("data", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("header", style);

        return styles;
    }

    /**
     * 创建单元格
     */
    public Cell createCell(Excel attr, Row row, int column)
    {
        // 创建列
        Cell cell = row.createCell(column);
        // 写入列信息
        cell.setCellValue(attr.name());
        setDataValidation(attr, row, column);
        cell.setCellStyle(styles.get("header"));
        return cell;
    }

    /**
     * 设置单元格信息
     *
     * @param value 单元格值
     * @param attr 注解相关
     * @param cell 单元格信息
     */
    public void setCellVo(Object value, Excel attr, Cell cell)
    {
        if (Excel.ColumnType.STRING == attr.cellType())
        {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(StringUtils.isNull(value) ? attr.defaultValue() : value + attr.suffix());
        }
        else if (Excel.ColumnType.NUMERIC == attr.cellType())
        {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(Integer.parseInt(value + ""));
        }
    }

    /**
     * 创建表格样式
     */
    public void setDataValidation(Excel attr, Row row, int column)
    {
        if (attr.name().indexOf("注：") >= 0)
        {
            sheet.setColumnWidth(column, 6000);
        }
        else
        {
            // 设置列宽
            sheet.setColumnWidth(column, (int) ((attr.width() + 0.72) * 256));
            row.setHeight((short) (attr.height() * 20));
        }
        // 如果设置了提示信息则鼠标放上去提示.
        if (StringUtils.isNotEmpty(attr.prompt()))
        {
            // 这里默认设了2-101列提示.
            setXSSFPrompt(sheet, "", attr.prompt(), 1, 100, column, column);
        }
        // 如果设置了combo属性则本列只能选择不能输入
        if (attr.combo().length > 0)
        {
            // 这里默认设了2-101列只能选择不能输入.
            setXSSFValidation(sheet, attr.combo(), 1, 100, column, column);
        }
    }

    /**
     * 添加单元格
     */
    public Cell addCell(Excel attr, Row row, T vo, Field field, int column)
    {
        Cell cell = null;
        try
        {
            // 设置行高
            row.setHeight((short) (attr.height() * 20));
            // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
            if (attr.isExport())
            {
                // 创建cell
                cell = row.createCell(column);
                cell.setCellStyle(styles.get("data"));

                // 用于读取对象中的属性
                Object value = getTargetValue(vo, field, attr);
                String dateFormat = attr.dateFormat();
                String readConverterExp = attr.readConverterExp();
                if (StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotNull(value))
                {
                    cell.setCellValue(DateUtils.parseDateToStr(dateFormat, (Date) value));
                }
                else if (StringUtils.isNotEmpty(readConverterExp) && StringUtils.isNotNull(value))
                {
                    cell.setCellValue(convertByExp(String.valueOf(value), readConverterExp));
                }
                else
                {
                    // 设置列类型
                    setCellVo(value, attr, cell);
                }
            }
        }
        catch (Exception e)
        {
            log.error("导出Excel失败{}", e);
        }
        return cell;
    }

    /**
     * 设置 POI XSSFSheet 单元格提示
     *
     * @param sheet 表单
     * @param promptTitle 提示标题
     * @param promptContent 提示内容
     * @param firstRow 开始行
     * @param endRow 结束行
     * @param firstCol 开始列
     * @param endCol 结束列
     */
    public void setXSSFPrompt(Sheet sheet, String promptTitle, String promptContent, int firstRow, int endRow,
                              int firstCol, int endCol)
    {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createCustomConstraint("DD1");
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        dataValidation.createPromptBox(promptTitle, promptContent);
        dataValidation.setShowPromptBox(true);
        sheet.addValidationData(dataValidation);
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param sheet 要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow 结束行
     * @param firstCol 开始列
     * @param endCol 结束列
     * @return 设置好的sheet.
     */
    public void setXSSFValidation(Sheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol)
    {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        // 加载下拉列表内容
        DataValidationConstraint constraint = helper.createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        // 处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation)
        {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        }
        else
        {
            dataValidation.setSuppressDropDownArrow(false);
        }

        sheet.addValidationData(dataValidation);
    }

    /**
     * 解析导出值 0=男,1=女,2=未知
     *
     * @param propertyValue 参数值
     * @param converterExp 翻译注解
     * @return 解析后值
     * @throws Exception
     */
    public static String convertByExp(String propertyValue, String converterExp) throws Exception
    {
        try
        {
            String[] convertSource = converterExp.split(",");
            for (String item : convertSource)
            {
                String[] itemArray = item.split("=");
                if (itemArray[0].equals(propertyValue))
                {
                    return itemArray[1];
                }
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        return propertyValue;
    }

    /**
     * 反向解析值 男=0,女=1,未知=2
     *
     * @param propertyValue 参数值
     * @param converterExp 翻译注解
     * @return 解析后值
     * @throws Exception
     */
    public static String reverseByExp(String propertyValue, String converterExp) throws Exception
    {
        try
        {
            String[] convertSource = converterExp.split(",");
            for (String item : convertSource)
            {
                String[] itemArray = item.split("=");
                if (itemArray[1].equals(propertyValue))
                {
                    return itemArray[0];
                }
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        return propertyValue;
    }

    /**
     * 编码文件名
     */
    public String encodingFilename(String filename)
    {
        filename =   filename +DateUtils.dateTime()+".xlsx";
        return filename;
    }

    /**
     * 获取下载路径
     *
     * @param filename 文件名称
     */
    public String getAbsoluteFile(String filename)
    {
        String downloadPath = Global.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }

        return downloadPath;
    }

    /**
     * 获取bean中的属性值
     *
     * @param vo 实体对象
     * @param field 字段
     * @param excel 注解
     * @return 最终的属性值
     * @throws Exception
     */
    private Object getTargetValue(T vo, Field field, Excel excel) throws Exception
    {
        Object o = field.get(vo);
        if (StringUtils.isNotEmpty(excel.targetAttr()))
        {
            String target = excel.targetAttr();
            if (target.indexOf(".") > -1)
            {
                String[] targets = target.split("[.]");
                for (String name : targets)
                {
                    o = getValue(o, name);
                }
            }
            else
            {
                o = getValue(o, target);
            }
        }
        return o;
    }

    /**
     * 以类的属性的get方法方法形式获取值
     *
     * @param o
     * @param name
     * @return value
     * @throws Exception
     */
    private Object getValue(Object o, String name) throws Exception
    {
        if (StringUtils.isNotEmpty(name))
        {
            Class<?> clazz = o.getClass();
            String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method method = clazz.getMethod(methodName);
            o = method.invoke(o);
        }
        return o;
    }

    /**
     * 得到所有定义字段
     */
    private void createExcelField()
    {
        this.fields = new ArrayList<Object[]>();
        List<Field> tempFields = new ArrayList<>();
        tempFields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        tempFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        for (Field field : tempFields)
        {
            // 单注解
            if (field.isAnnotationPresent(Excel.class))
            {
                putToField(field, field.getAnnotation(Excel.class));
            }

            // 多注解
            if (field.isAnnotationPresent(Excels.class))
            {
                Excels attrs = field.getAnnotation(Excels.class);
                Excel[] excels = attrs.value();
                for (Excel excel : excels)
                {
                    putToField(field, excel);
                }
            }
        }
    }

    /**
     * 放到字段集合中
     */
    private void putToField(Field field, Excel attr)
    {
        if (attr != null && (attr.type() == Excel.Type.ALL || attr.type() == type))
        {
            this.fields.add(new Object[] { field, attr });
        }
    }

    /**
     * 创建一个工作簿
     */
    public void createWorkbook()
    {
        this.wb = new SXSSFWorkbook(500);
    }

    /**
     * 创建工作表
     *
     * @param sheetNo sheet数量
     * @param index 序号
     */
    public void createSheet(double sheetNo, int index)
    {
        this.sheet = wb.createSheet();
        this.styles = createStyles(wb);
        // 设置工作表的名称.
        if (sheetNo == 0)
        {
            wb.setSheetName(index, sheetName);
        }
        else
        {
            wb.setSheetName(index, sheetName + index);
        }
    }

    /**
     * 获取单元格值
     *
     * @param row 获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    public Object getCellValue(Row row, int column)
    {
        if (row == null)
        {
            return row;
        }
        Object val = "";
        try
        {
            Cell cell = row.getCell(column);
            if (cell != null)
            {
                if (cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA)
                {
                    val = cell.getNumericCellValue();
                    if (HSSFDateUtil.isCellDateFormatted(cell))
                    {
                        val = DateUtil.getJavaDate((Double) val); // POI Excel 日期格式转换
                    }
                    else
                    {
                        if ((Double) val % 1 > 0)
                        {
                            val = new DecimalFormat("0.00").format(val);
                        }
                        else
                        {
                            val = new DecimalFormat("0").format(val);
                        }
                    }
                }
                else if (cell.getCellTypeEnum() == CellType.STRING)
                {
                    val = cell.getStringCellValue();
                }
                else if (cell.getCellTypeEnum() == CellType.BOOLEAN)
                {
                    val = cell.getBooleanCellValue();
                }
                else if (cell.getCellTypeEnum() == CellType.ERROR)
                {
                    val = cell.getErrorCellValue();
                }

            }
        }
        catch (Exception e)
        {
            return val;
        }
        return val;
    }


    /**
     * 导出入库导入模板
     *
     * @return 结果
     */
    public AjaxResult exportStoreInModel(String[] field,String[] whInfo,String[] product,String sheetName)
    {
        this.init(null, sheetName, Excel.Type.IMPORT);
        OutputStream out = null;
        createSheet(0, 0);
        DataValidationHelper helper = sheet.getDataValidationHelper();//设置下拉框xlsx格式
        try
        {
            Row row=sheet.createRow(0);

            for(int i=0;i<field.length;i++){
                //创建列
                Cell cell=row.createCell(i);
                cell.setCellType(CellType.STRING);
                CellStyle cellStyle=wb.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                if (field[i].indexOf("注：")>=0){
                    Font font=wb.createFont();
                    font.setColor(HSSFFont.COLOR_RED);
                    cellStyle.setFont(font);
                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    sheet.setColumnWidth(i,6000);
                } else if(field[i].indexOf("数量")>=0){
                    Font font=wb.createFont();
                    //粗体显示
                    font.setBold(true);
                    //选择需要用到的字体格式
                    cellStyle.setFont(font);
                    //设置单元格背景颜色
                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    sheet.setColumnWidth(i,3766);
                    cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
                } else{
                    Font font=wb.createFont();
                    //粗体显示
                    font.setBold(true);
                    //选择需要用到的字体格式
                    cellStyle.setFont(font);
                    //设置单元格背景颜色
                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    sheet.setColumnWidth(i,3766);
                }
                //设置背景颜色
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setWrapText(true);
                cell.setCellStyle(cellStyle);
                //写入列名
                cell.setCellValue(field[i]);

            }
            ExcelUtil.creatDropDownList(wb,sheet,"whList",helper,whInfo,1,65536,3,3);


//            ExcelUtil.creatDropDownList(wb,sheet,"productList",helper,product,1,65536,5,5);

            String filename = encodingFilename(sheetName);
            out = new FileOutputStream(getAbsoluteFile(filename));
            wb.write(out);
            return AjaxResult.success(filename);
        }
        catch (Exception e)
        {
            log.error("导出Excel异常{}", e.getMessage());
            throw new BusinessException("导出Excel失败，请联系网站管理员！");
        }
        finally
        {
            if (wb != null)
            {
                try
                {
                    wb.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }


    /**
     * 创建excel下拉框,以sheet工作表保存select信息
     * @param sheet
     * @param helper
     * @param list
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     */
    public static void creatDropDownList(Workbook wb,Sheet sheet,String selectName,DataValidationHelper helper,String[] list,
                                         Integer firstRow,Integer lastRow,Integer firstCol,Integer lastCol){

        // 创建下拉列表值存储工作表并设置值
        genearteOtherSheet(wb, list,selectName);
        XSSFDataValidationConstraint constraint = new XSSFDataValidationConstraint(DataValidationConstraint.ValidationType.LIST,selectName+"!$A$1:$A$"+list.length);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,lastRow,firstCol,lastCol);
        // 数据有效性对象
//        DataValidationHelper help = new XSSFDataValidationHelper(sheet);
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        if (dataValidation instanceof XSSFDataValidation){
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        }else{
            dataValidation.setSuppressDropDownArrow(false);
        }
        sheet.addValidationData(dataValidation);
        // 隐藏作为下拉列表值的Sheet
        wb.setSheetHidden(wb.getSheetIndex(selectName), 1);
    }


    // 创建下拉列表值存储工作表并设置值
    public static void genearteOtherSheet(Workbook wb, String[] typeArrays,String sheetName) {
        // 创建下拉列表值存储工作表
        Sheet sheet = wb.createSheet(sheetName);
        // 循环往该sheet中设置添加下拉列表的值
        for (int i = 0; i < typeArrays.length; i++) {
            Row row = sheet.createRow(i);
            Cell cell = row.createCell((int) 0);
            cell.setCellValue(typeArrays[i]);
        }
    }




    public void setDataCellStyles(HSSFWorkbook workbook, HSSFSheet sheet) {
        cellStyle = workbook.createCellStyle();
        // 设置边框
//        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 设置背景色
        cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
//        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        // 设置居中
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        // 设置字体
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 11); // 设置字体大小
        cellStyle.setFont(font);// 选择需要用到的字体格式
        // 设置单元格格式为文本格式（这里还可以设置成其他格式,可以自行百度）
        HSSFDataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
    }

    /**
     * 创建数据域（下拉联动的数据）
     *  @param workbook
     * @param hideSheetName
     * @param productList
     */
    private void creatHideSheet(Workbook workbook, String hideSheetName, List<List<String>> productList, List<String> warehouseList) {
        // 创建数据域
        Sheet sheet = workbook.createSheet(hideSheetName);
        // 用于记录行
        int rowRecord = 0;
        // 获取行（从0下标开始）
        Row warehouseRow = sheet.createRow(rowRecord);
        // 创建仓库数据
        this.creatRow(warehouseRow, warehouseList);
        // 根据省份插入对应的市信息
        rowRecord++;
        for (List<String> stringList :productList) {
            //获取行
            Row Cityrow = sheet.createRow(rowRecord);
            // 创建省份数据
            this.creatRow(Cityrow, stringList);
            rowRecord++;
        }
    }

    /**
     * 创建一列数据
     *
     * @param currentRow
     * @param textList
     */
    public void creatRow(Row currentRow, List<String> text) {
        if (text != null) {
            int i = 0;
            for (String cellValue : text) {
                // 注意列是从（1）下标开始
                Cell userNameLableCell = currentRow.createCell(i++);
                userNameLableCell.setCellValue(cellValue);
            }
        }
    }

    /**
     * 名称管理
     *
     * @param workbook
     * @param hideSheetName
     *            数据域的sheet名
     */
    private void creatExcelNameList(Workbook workbook, String hideSheetName, List<List<String>> productList, List<String> warehouseList) {
        Name name;
        name = workbook.createName();
        // 设置省名称
        name.setNameName("warehouse");
        name.setRefersToFormula(hideSheetName + "!$A$1:$"
                + this.getcellColumnFlag(warehouseList.size())+ "$1");
        // 设置省下面的市

        for (int i = 0; i < productList.size(); i++) {
            List<String> product=productList.get(i);
            name = workbook.createName();
            name.setNameName(product.get(0));
//            name.setNameName(product.get(0).split("\\+")[1]);
            name.setRefersToFormula(hideSheetName + "!$B$" + (i + 2) + ":$"
                    + this.getcellColumnFlag(productList.get(i).size()) + "$" + (i + 2));
        }
    }

    // 根据数据值确定单元格位置（比如：28-AB）
    private String getcellColumnFlag(int num) {
        String columFiled = "";
        int chuNum = 0;
        int yuNum = 0;
        if (num >= 1 && num <= 26) {
            columFiled = this.doHandle(num);
        } else {
            chuNum = num / 26;
            yuNum = num % 26;

            columFiled += this.doHandle(chuNum);
            columFiled += this.doHandle(yuNum);
        }
        return columFiled;
    }

    private String doHandle(final int num) {
        String[] charArr = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z" };
        return charArr[num - 1].toString();
    }

    /**
     * 使用已定义的数据源方式设置一个数据验证
     *
     * @param formulaString
     * @param naturalRowIndex
     * @param naturalColumnIndex
     * @return
     */
    public DataValidation getDataValidationByFormula(String formulaString,
                                                     int naturalRowIndex, int naturalColumnIndex,DataValidationHelper helper) {

        // 加载下拉列表内容
//        DVConstraint constraint = DVConstraint
//                .createFormulaListConstraint(formulaString);
        DataValidationConstraint dvConstraint = helper.createFormulaListConstraint(formulaString);
        // 设置数据有效性加载在哪个单元格上。
        // 四个参数分别是：起始行、终止行、起始列、终止列
        int firstRow = naturalRowIndex;
        int lastRow = naturalRowIndex;
        int firstCol = naturalColumnIndex - 1;
        int lastCol = naturalColumnIndex - 1;
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,
                lastRow, firstCol, lastCol);
        // 数据有效性对象
        DataValidation data_validation_list =helper.createValidation(dvConstraint,regions);
        return data_validation_list;
    }

    /**
     * 创建一列数据
     *
     * @param hssfSheet
     */
    public void creatAppRow(Sheet hssfSheet, int naturalRowIndex,DataValidationHelper helper) {
        // 获取行
        Row hssfRow = hssfSheet.createRow(naturalRowIndex);

        Cell warehouse = hssfRow.createCell(0);
        warehouse.setCellValue("");
        warehouse.setCellStyle(cellStyle);

        Cell product = hssfRow.createCell(5);
        product.setCellValue("");
        product.setCellStyle(cellStyle);

        // 得到验证对象
        DataValidation data_validation_list1 = this.getDataValidationByFormula(
                "warehouse", naturalRowIndex, 1,helper);
        DataValidation data_validation_list2 = this
                .getDataValidationByFormula("INDIRECT($A"
                        + (naturalRowIndex + 1) + ")", naturalRowIndex, 6,helper);
        // 工作表添加验证数据
        hssfSheet.addValidationData(data_validation_list1);
        hssfSheet.addValidationData(data_validation_list2);
    }

//    public void Export() {
//        try {
//            File file = new File("F:/excel.xls");
//            FileOutputStream outputStream = new FileOutputStream(file);
//            // 创建excel
//            HSSFWorkbook workbook = new HSSFWorkbook();
//            // 设置sheet 名称
//            HSSFSheet excelSheet = workbook.createSheet("excel");
//            // 设置样式
//            this.setDataCellStyles(workbook, excelSheet);
//            // 创建一个隐藏页和隐藏数据集
//            this.creatHideSheet(workbook, "shutDataSource", productList, );
//            // 设置名称数据集
//            this.creatExcelNameList(workbook, "shutDataSource");
//            // 创建一行数据
//            for (int i = 0; i < 50; i++) {
//                this.creatAppRow(excelSheet,i);
//
//            }
//            workbook.write(outputStream);
//            outputStream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



    /**
     * 移库模板
     * @param field
     * @param storeMoveModel
     * @return
     */
//    public AjaxResult exportStoreMoveModel(String[] field, List<List<String>> productList, List<String> warehouseList, String[] shString, String sheetName) {
//        this.init(null, sheetName, Excel.Type.IMPORT);
//        OutputStream out = null;
//        createSheet(0, 0);
//        DataValidationHelper helper = sheet.getDataValidationHelper();//设置下拉框xlsx格式
//        try
//        {
//            Row row=sheet.createRow(0);
//
//            for(int i=0;i<field.length;i++){
//                //创建列
//                Cell cell=row.createCell(i);
//                cell.setCellType(CellType.STRING);
//                CellStyle cellStyle=wb.createCellStyle();
//                cellStyle.setAlignment(HorizontalAlignment.CENTER);
//                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//                if (field[i].indexOf("注：")>=0){
//                    Font font=wb.createFont();
//                    font.setColor(HSSFFont.COLOR_RED);
//                    cellStyle.setFont(font);
//                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//                    sheet.setColumnWidth(i,6000);
//                } else if(field[i].indexOf("数量")>=0){
//                    Font font=wb.createFont();
//                    //粗体显示
//                    font.setBold(true);
//                    //选择需要用到的字体格式
//                    cellStyle.setFont(font);
//                    //设置单元格背景颜色
//                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//                    sheet.setColumnWidth(i,3766);
//                    cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
//                }else if(field[i].indexOf("起始库位")>=0){
//                    Font font=wb.createFont();
//                    //粗体显示
//                    font.setBold(true);
//                    //选择需要用到的字体格式
//                    cellStyle.setFont(font);
//                    //设置单元格背景颜色
//                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//                    sheet.setColumnWidth(i,8000);
//                }
//                else{
//                    Font font=wb.createFont();
//                    //粗体显示
//                    font.setBold(true);
//                    //选择需要用到的字体格式
//                    cellStyle.setFont(font);
//                    //设置单元格背景颜色
//                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//                    sheet.setColumnWidth(i,3766);
//                }
//                //设置背景颜色
//                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//                cellStyle.setWrapText(true);
//                cell.setCellStyle(cellStyle);
//                //写入列名
//                cell.setCellValue(field[i]);
//
//            }
//            // 创建一个隐藏页和隐藏数据集
//            this.creatHideSheet(wb, "shutDataSource",productList,warehouseList);
//            // 设置名称数据集
//            this.creatExcelNameList(wb, "shutDataSource",productList,warehouseList);
//
//            // 创建一行数据
//            for (int i = 1; i < 2; i++) {
//                this.creatAppRow(sheet,i,helper);
//            }
//            // 隐藏作为关联下拉列表值的Sheet
//            wb.setSheetHidden(wb.getSheetIndex("shutDataSource"), 1);
////            ExcelUtil.creatDropDownList(wb,sheet,"warehouseList",helper,shString,1,65536,7,7);
////            setXSSFValidation(sheet,shString,1,65536,7,7);
//            String filename = encodingFilename(sheetName);
//            out = new FileOutputStream(getAbsoluteFile(filename));
//            wb.write(out);
//            return AjaxResult.success(filename);
//        }
//        catch (Exception e)
//        {
//            log.error("导出Excel异常{}", e.getMessage());
//            throw new BusinessException("导出Excel失败，请联系网站管理员！");
//        }
//        finally
//        {
//            if (wb != null)
//            {
//                try
//                {
//                    wb.close();
//                }
//                catch (IOException e1)
//                {
//                    e1.printStackTrace();
//                }
//            }
//            if (out != null)
//            {
//                try
//                {
//                    out.close();
//                }
//                catch (IOException e1)
//                {
//                    e1.printStackTrace();
//                }
//            }
//        }
//
//    }
    public AjaxResult exportStoreMoveModel(String[] field, String sheetName) {
        this.init(null, sheetName, Excel.Type.IMPORT);
        OutputStream out = null;
        createSheet(0, 0);
//        DataValidationHelper helper = sheet.getDataValidationHelper();//设置下拉框xlsx格式
        try
        {
            Row row=sheet.createRow(0);

            for(int i=0;i<field.length;i++){
                //创建列
                Cell cell=row.createCell(i);
                cell.setCellType(CellType.STRING);
                CellStyle cellStyle=wb.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                if (field[i].indexOf("注：")>=0){
                    Font font=wb.createFont();
                    font.setColor(HSSFFont.COLOR_RED);
                    cellStyle.setFont(font);
                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    sheet.setColumnWidth(i,6000);
                } else if(field[i].indexOf("数量")>=0){
                    Font font=wb.createFont();
                    //粗体显示
                    font.setBold(true);
                    //选择需要用到的字体格式
                    cellStyle.setFont(font);
                    //设置单元格背景颜色
                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    sheet.setColumnWidth(i,3766);
                    cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
                }else if(field[i].indexOf("起始库位")>=0){
                    Font font=wb.createFont();
                    //粗体显示
                    font.setBold(true);
                    //选择需要用到的字体格式
                    cellStyle.setFont(font);
                    //设置单元格背景颜色
                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    sheet.setColumnWidth(i,8000);
                }
                else{
                    Font font=wb.createFont();
                    //粗体显示
                    font.setBold(true);
                    //选择需要用到的字体格式
                    cellStyle.setFont(font);
                    //设置单元格背景颜色
                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    sheet.setColumnWidth(i,3766);
                }
                //设置背景颜色
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setWrapText(true);
                cell.setCellStyle(cellStyle);
                //写入列名
                cell.setCellValue(field[i]);

            }
            String filename = encodingFilename(sheetName);
            out = new FileOutputStream(getAbsoluteFile(filename));
            wb.write(out);
            return AjaxResult.success(filename);
        }
        catch (Exception e)
        {
            log.error("导出Excel异常{}", e.getMessage());
            throw new BusinessException("导出Excel失败，请联系网站管理员！");
        }
        finally
        {
            if (wb != null)
            {
                try
                {
                    wb.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }

    }


    /**
     * 盘库模板
     * @param field
     * @param storeMoveModel
     * @return
     */
    public AjaxResult exportStoreCheckModel(String[] field, String storeMoveModel) {
        this.init(null, storeMoveModel, Excel.Type.IMPORT);
        OutputStream out = null;
        createSheet(0, 0);
//        DataValidationHelper helper = sheet.getDataValidationHelper();//设置下拉框xlsx格式
        try
        {
            Row row=sheet.createRow(0);

            for(int i=0;i<field.length;i++){
                //创建列
                Cell cell=row.createCell(i);
                cell.setCellType(CellType.STRING);
                CellStyle cellStyle=wb.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                if (field[i].indexOf("注：")>=0){
                    Font font=wb.createFont();
                    font.setColor(HSSFFont.COLOR_RED);
                    cellStyle.setFont(font);
                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    sheet.setColumnWidth(i,6000);
                } else if(field[i].indexOf("数量")>=0){
                    Font font=wb.createFont();
                    //粗体显示
                    font.setBold(true);
                    //选择需要用到的字体格式
                    cellStyle.setFont(font);
                    //设置单元格背景颜色
                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    sheet.setColumnWidth(i,3766);
                    cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
                }
                else{
                    Font font=wb.createFont();
                    //粗体显示
                    font.setBold(true);
                    //选择需要用到的字体格式
                    cellStyle.setFont(font);
                    //设置单元格背景颜色
                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    sheet.setColumnWidth(i,3766);
                }
                //设置背景颜色
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setWrapText(true);
                cell.setCellStyle(cellStyle);
                //写入列名
                cell.setCellValue(field[i]);

            }
//            // 创建一个隐藏页和隐藏数据集
//            this.creatHideSheet(wb, "shutDataSource",productList,warehouseList);
//            // 设置名称数据集
//            this.creatExcelNameList(wb, "shutDataSource",productList,warehouseList);
//
//            // 创建一行数据
//            for (int i = 1; i < 1000; i++) {
//                this.creatAppRow(sheet,i,helper);
//            }

            String filename = encodingFilename(sheetName);
            out = new FileOutputStream(getAbsoluteFile(filename));
            wb.write(out);
            return AjaxResult.success(filename);
        }
        catch (Exception e)
        {
            log.error("导出Excel异常{}", e.getMessage());
            throw new BusinessException("导出Excel失败，请联系网站管理员！");
        }
        finally
        {
            if (wb != null)
            {
                try
                {
                    wb.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }
//    public AjaxResult exportStoreCheckModel(String[] field, List<List<String>> productList, List<String> warehouseList, String storeMoveModel) {
//        this.init(null, storeMoveModel, Excel.Type.IMPORT);
//        OutputStream out = null;
//        createSheet(0, 0);
//        DataValidationHelper helper = sheet.getDataValidationHelper();//设置下拉框xlsx格式
//        try
//        {
//            Row row=sheet.createRow(0);
//
//            for(int i=0;i<field.length;i++){
//                //创建列
//                Cell cell=row.createCell(i);
//                cell.setCellType(CellType.STRING);
//                CellStyle cellStyle=wb.createCellStyle();
//                cellStyle.setAlignment(HorizontalAlignment.CENTER);
//                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//                if (field[i].indexOf("注：")>=0){
//                    Font font=wb.createFont();
//                    font.setColor(HSSFFont.COLOR_RED);
//                    cellStyle.setFont(font);
//                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//                    sheet.setColumnWidth(i,6000);
//                } else if(field[i].indexOf("数量")>=0){
//                    Font font=wb.createFont();
//                    //粗体显示
//                    font.setBold(true);
//                    //选择需要用到的字体格式
//                    cellStyle.setFont(font);
//                    //设置单元格背景颜色
//                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//                    sheet.setColumnWidth(i,3766);
//                    cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
//                }
//                else{
//                    Font font=wb.createFont();
//                    //粗体显示
//                    font.setBold(true);
//                    //选择需要用到的字体格式
//                    cellStyle.setFont(font);
//                    //设置单元格背景颜色
//                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//                    sheet.setColumnWidth(i,3766);
//                }
//                //设置背景颜色
//                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//                cellStyle.setWrapText(true);
//                cell.setCellStyle(cellStyle);
//                //写入列名
//                cell.setCellValue(field[i]);
//
//            }
//            // 创建一个隐藏页和隐藏数据集
//            this.creatHideSheet(wb, "shutDataSource",productList,warehouseList);
//            // 设置名称数据集
//            this.creatExcelNameList(wb, "shutDataSource",productList,warehouseList);
//
//            // 创建一行数据
//            for (int i = 1; i < 1000; i++) {
//                this.creatAppRow(sheet,i,helper);
//            }
//
//            String filename = encodingFilename(sheetName);
//            out = new FileOutputStream(getAbsoluteFile(filename));
//            wb.write(out);
//            return AjaxResult.success(filename);
//        }
//        catch (Exception e)
//        {
//            log.error("导出Excel异常{}", e.getMessage());
//            throw new BusinessException("导出Excel失败，请联系网站管理员！");
//        }
//        finally
//        {
//            if (wb != null)
//            {
//                try
//                {
//                    wb.close();
//                }
//                catch (IOException e1)
//                {
//                    e1.printStackTrace();
//                }
//            }
//            if (out != null)
//            {
//                try
//                {
//                    out.close();
//                }
//                catch (IOException e1)
//                {
//                    e1.printStackTrace();
//                }
//            }
//        }
//    }

    public AjaxResult exportStoreOutModel(String[] field, List<List<String>> productList, List<String> warehouseList, String sheetName) {
        this.init(null, sheetName, Excel.Type.IMPORT);
        OutputStream out = null;
        createSheet(0, 0);
        DataValidationHelper helper = sheet.getDataValidationHelper();//设置下拉框xlsx格式
        try
        {
            Row row=sheet.createRow(0);

            for(int i=0;i<field.length;i++){
                //创建列
                Cell cell=row.createCell(i);
                cell.setCellType(CellType.STRING);
                CellStyle cellStyle=wb.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                if (field[i].indexOf("注：")>=0){
                    Font font=wb.createFont();
                    font.setColor(HSSFFont.COLOR_RED);
                    cellStyle.setFont(font);
                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    sheet.setColumnWidth(i,6000);
                } else if(field[i].indexOf("数量")>=0){
                    Font font=wb.createFont();
                    //粗体显示
                    font.setBold(true);
                    //选择需要用到的字体格式
                    cellStyle.setFont(font);
                    //设置单元格背景颜色
                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    sheet.setColumnWidth(i,3766);
                    cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
                }
                else{
                    Font font=wb.createFont();
                    //粗体显示
                    font.setBold(true);
                    //选择需要用到的字体格式
                    cellStyle.setFont(font);
                    //设置单元格背景颜色
                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    sheet.setColumnWidth(i,3766);
                }
                //设置背景颜色
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setWrapText(true);
                cell.setCellStyle(cellStyle);
                //写入列名
                cell.setCellValue(field[i]);

            }
            // 创建一个隐藏页和隐藏数据集
            this.creatHideSheet(wb, "shutDataSource",productList,warehouseList);
            // 设置名称数据集
            this.creatExcelNameList(wb, "shutDataSource",productList,warehouseList);

            // 创建一行数据
            for (int i = 1; i < 2; i++) {
                this.creatAppRow(sheet,i,helper);
            }

            String filename = encodingFilename(sheetName);
            out = new FileOutputStream(getAbsoluteFile(filename));
            wb.write(out);
            return AjaxResult.success(filename);
        }
        catch (Exception e)
        {
            log.error("导出Excel异常{}", e.getMessage());
            throw new BusinessException("导出Excel失败，请联系网站管理员！");
        }
        finally
        {
            if (wb != null)
            {
                try
                {
                    wb.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }
}
