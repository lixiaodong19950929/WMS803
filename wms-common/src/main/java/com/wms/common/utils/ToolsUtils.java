package com.wms.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: wms
 * @description: 系统工具类
 * @author: 刺客
 * @create: 2019-12-27 14:32
 */
public class ToolsUtils {

    /** 生成编号 随机获取时间戳加五位随机数*/
    public static String getOrdersId(String title) {
        return title + collectionsNum(DateUtils.dateTime() + getRandomNum(5));
    }

    public static String collectionsNum(String strNum) {
        char[] str = strNum.toCharArray();
        List<String> randRum = new ArrayList<String>();
        for (int i = 0; i < str.length; i++) {
            randRum.add(String.valueOf(str[i]));
        }
        Collections.shuffle(randRum);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < randRum.size(); i++) {
            sb.append(randRum.get(i));
        }
        return sb.toString().trim();
    }

    //获取随机数字
    public static String getRandomNum(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(10);// [0,62)
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


    /**
     * 设备编码（由编号类型化编码+0001）
     *
     * @param deviceType,deviceNo 编码类型 STI
     * @return
     * @throw Exception
     */
    public static String getNewDeviceNo(String deviceType, String deviceNo) {
        String newDeviceNo = "0001";
        if (deviceNo != null && !deviceNo.isEmpty()) {
            int newDevice = Integer.parseInt(deviceNo) + 1;
            newDeviceNo = String.format(deviceType + "%04d", newDevice);
        }
        return newDeviceNo;
    }

    /**
     * 日期转换成时间戳
     */
    public static long dateToStamp(String s) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long nowDate = date.getTime() / 1000;
        return nowDate;
    }

    /**
     * 生成订单编码号（由编号类型化编码+8位日期+6位随机数组成）
     *
     * @param numType 编码类型 dw
     * @return
     * @throw Exception
     */
    public static String createOrderNumber(String numType, String time) {
        //格式化日期为“YYYYmmdd”
        DateFormat format = new SimpleDateFormat("YYYYmmddHHmmss");
        //Date date = new Date();
        StringBuffer buffer = new StringBuffer();
        buffer.append(numType);
        buffer.append(format.format(time));
        buffer.append(getRandNum(6));
        return buffer.toString();
    }

    /**
     * 获取六位随机数
     *
     * @return
     * @Param leng 随机长度
     */
    public static String getRandNum(int leng) {
        Random random = new Random();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < leng; i++) {
            result.append(random.nextInt(10));
        }
        if (result.length() > 0) {
            return result.toString();
        }
        return null;
    }

    public static String updateToLower(String str) {
        char[] s = str.toCharArray();
        for (int i = 0; i < s.length; i++) {
            //判断是否是大写，是大写的话就转换成小写
            if (Character.isUpperCase(s[i])) {
                s[i] = (char) ((int) s[i] + 32);
                //判断是否是数字，是数字就不变
            } else if (Character.isDigit(s[i])) {
                s[i] = s[i];
            }
        }
        //将字符数组转成字符串
        String string = new String(s);
        return string;
    }

    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static String currentTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = format.format(date);
        return nowDate;
    }


}
