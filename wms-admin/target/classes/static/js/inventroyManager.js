var used = '' //已使用库位
var unUsed=''//未使用库位
var cangKuName=''//仓库名称
var cangKuCode=''//仓库编码
//库存数据接口
/*$.ajax({
    cache:true,
    type: 'POST',
    // data:{whcode},
    url: '/warehouse/inventroyManager/getinventroyManagerByStock',
    success: function (result) {
        var data=result.data;
        $("#storeHouseCount").html(data.storeHouseCount+'个')
        $("#useStoreHouseCount").html(data.useStoreHouseCount+'个')
        $("#storeInCount").html(data.storeInCount+'个')
        $("#storeOutCount").html(data.storeOutCount+'个')
        $("#storeMoveCount").html(data.storeMoveCount+'个')
        used = data.useStoreHouseCount;
        unUsed = data.storeHouseCount - data.useStoreHouseCount;
        echartsss();
        //查询模块selectList
        /!*var option = '<option value="">仓库名称</option>';
        for(var i = 0;i<data.list.length;i++){
            cangKuCode = data.list[i].whcode;
            cangKuName = data.list[i].whname;
            option += '<option value="'+cangKuCode+'">'+cangKuName+'</option>'

        }*!/
        // $("#selectList").html(option)
    }
})*/
function echartsss() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echart'));
// 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        grid: {
            containLabel: true
        },
        graphic: {
            type: 'text',
            left: 'center',
            top: 'center',
            z: 2,
            // zlevel: 100,
            style: {
                text: '库存量',
                width: 100,
                height: 30,
                font: '1.5vw Microsoft YaHei'
            }
        },
        series: [
            {
                name: '库存量',
                type: 'pie',
                radius: ['40%', '60%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    }
                },
                data: [
                    {value: used, name: '已使用'},
                    {value: unUsed, name: '未使用'},
                ],
                color: ['#68D029', '#0079f4']
            }
        ]
    };
// 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    setTimeout(function () {
        window.onresize = function () {
            myChart.resize();
        }
    }, 200)

}


//数字小于10，前边补0
function TwoNumber (num) {
    if (num < 10) {
        num = '0' + num
    }
    return num
}
//判断某个元素是否在数组中
Array.prototype.S=String.fromCharCode(2);
Array.prototype.in_array=function(e){
    var r=new RegExp(this.S+e+this.S);
    return (r.test(this.S+this.join(this.S)+this.S));
};
var arrdata = []
var arrdetail = []
window.onload = function makeForm() {
    arrdata = []
    arrdetail = []
    $.ajax({
        cache:true,
        type: 'POST',
        // data:{
        //     "whcode":'WH0003'
        // },
        url: '/warehouse/inventroyManager/getinventroyManagerByStock',
        success: function (result) {
            var data=result.data;
            for(var i = 0;i<data.list.length;i++){
                arrdata.push(data.list[i].storehousecode)
                arrdetail.push({id:data.list[i].storehousecode,name:data.list[i].productname,Model:data.list[i].productmodel,time:data.list[i].createdate,storehouse:data.list[i].storehousecode})
            }
            $("#storeHouseCount").html(data.storeHouseCount+'个')
            $("#useStoreHouseCount").html(data.useStoreHouseCount+'个')
            $("#storeInCount").html(data.storeInCount+'个')
            $("#storeOutCount").html(data.storeOutCount+'个')
            $("#storeMoveCount").html(data.storeMoveCount+'个')
            used = data.useStoreHouseCount;
            unUsed = data.storeHouseCount - data.useStoreHouseCount;
            echartsss();
            tableFor(arrdata,arrdetail)
        }
    })

}
function searchTablestwo(whcode,startTime,endTime,barcode) {
    arrdata = []
    arrdetail = []
    $.ajax({
        cache:true,
        type: 'POST',
        data:{
            "whcode":whcode,
            "beginCreatedate":startTime,
            "endCreatedate":endTime,
            "key":barcode,
        },
        url: '/warehouse/inventroyManager/getinventroyManagerByStock',
        success: function (result) {
            arrdata = []
            arrdetail = []
            var data=result.data;
            for(var i = 0;i<data.list.length;i++){
                arrdata.push(data.list[i].storehousecode)
                arrdetail.push({id:data.list[i].storehousecode,name:data.list[i].productname,Model:data.list[i].productmodel,time:data.list[i].createdate,storehouse:data.list[i].storehousecode})

            }
            tableFor(arrdata,arrdetail)
        }
    })
}
function tableFor(arrdata,arrdetail) {
    //左边的表格
    var tab1 = '';
    for (var i = 1; i <= 11; i++) {
        var tr_string = '<tr>';
        for (var j = 4; j >= 1; j--) {
            var iiss = ""
            iiss = "01" + TwoNumber(i) + '0' + j
            if (arrdata.in_array(iiss) == true) {
                // tr_string += '<td id="01' + TwoNumber(i) + '0' +j + '"><img src="../img/missile_green.png"  style="height:40%;height:35%"></td>'
                var Store_id = '01'+ TwoNumber(i)+'0'+j
                for (var k = 0; k<arrdetail.length; k++){
                    if (arrdetail[k].id == Store_id){
                        tr_string += '<td id="01' + TwoNumber(i) + '0' +j + '" onmousemove="stockdetail(id,arrdetail)" onmouseout="hidedetail()"><img src="../img/missile_green.png"  style="height:40%;height:35%"></td>'
                        // stockdetail(arrdetail[k].name,arrdetail[k].Model,arrdetail[k].time,arrdetail[k].Storehouse)
                    }
                }
            }else {
                tr_string += '<td id="01' + TwoNumber(i) + '0' +j + '"><img src="../img/missile.png"  style="height:40%;height:35%"></td>'
            }
            //j为层,i为列,列的顺序为从上到下
            // tr_string += '<td id="01' + TwoNumber(i) + '0' +j + '"><img src="../img/missile.png"  style="height:40%;height:35%"></td>'
            // arrId.push({id:"01" + TwoNumber(i) + '0' + j})
        }
        tr_string += '</tr>'
        tab1 += tr_string;
    }
    $("#table_one").html(tab1);
    //中间第一个表格
    var tab2 = ''
    for (var i = 1; i <= 10; i++) {
        var tr_string = '<tr>';
        for (var j = 1; j <= 5; j++) {
            var iiss = ""
            iiss = "02" + TwoNumber(i) + '0' + j
            if (arrdata.in_array(iiss) == true) {
                var Store_id = '02'+ TwoNumber(i)+'0'+j
                for (var k = 0; k<arrdetail.length; k++){
                    if (arrdetail[k].id == Store_id){
                        tr_string += '<td id="02' + TwoNumber(i) + '0' +j + '" onmousemove="stockdetail(id,arrdetail)" onmouseout="hidedetail()"><img src="../img/missile_green.png"  style="height:40%;height:35%"></td>'
                    }
                }
            }else {
                tr_string += '<td id="02' + TwoNumber(i) + '0' +j + '"><img src="../img/missile.png"  style="height:40%;height:35%"></td>'
            }
            // arrId.push({id:"02" + TwoNumber(i) + '0' + j})
        }
        tr_string += '</tr>'
        tab2 += tr_string;
    }
    $("#table_two").html(tab2);
    //中间第二个表格
    var tab3 = ''
    for (var i = 1; i <= 10; i++) {
        var tr_string = '<tr>';
        for (var j = 5; j >= 1; j--) {
            var iiss = ""
            iiss = "03" + TwoNumber(i) + '0' + j
            if (arrdata.in_array(iiss) == true) {
                var Store_id = '03'+ TwoNumber(i)+'0'+j
                for (var k = 0; k<arrdetail.length; k++){
                    if (arrdetail[k].id == Store_id){
                        tr_string += '<td id="03' + TwoNumber(i) + '0' +j + '" onmousemove="stockdetail(id,arrdetail)" onmouseout="hidedetail()"><img src="../img/missile_green.png"  style="height:40%;height:35%"></td>'
                    }
                }
            }else {
                tr_string += '<td id="03' + TwoNumber(i) + '0' +j + '"><img src="../img/missile.png"  style="height:40%;height:35%"></td>'
            }
            // arrId.push({id:"03" + TwoNumber(i) + '0' + j})
        }
        tr_string += '</tr>'
        tab3 += tr_string;
    }
    $("#table_three").html(tab3);
    //中间第三个表格
    var tab4 = ''
    for (var i = 1; i <= 10; i++) {
        var tr_string = '<tr>';
        for (var j = 1; j <= 5; j++) {
            var iiss = ""
            iiss = "04" + TwoNumber(i) + '0' + j
            if (arrdata.in_array(iiss) == true) {
                var Store_id = '04'+ TwoNumber(i)+'0'+j
                for (var k = 0; k<arrdetail.length; k++){
                    if (arrdetail[k].id == Store_id){
                        tr_string += '<td id="04' + TwoNumber(i) + '0' +j + '" onmousemove="stockdetail(id,arrdetail)" onmouseout="hidedetail()"><img src="../img/missile_green.png"  style="height:40%;height:35%"></td>'
                    }
                }
            }else {
                tr_string += '<td id="04' + TwoNumber(i) + '0' +j + '"><img src="../img/missile.png"  style="height:40%;height:35%"></td>'
            }
            // arrId.push({id:"04" + TwoNumber(i) + '0' + j})
        }
        tr_string += '</tr>'
        tab4 += tr_string;
    }
    $("#table_four").html(tab4);
    //中间第四个表格
    var tab5 = ''
    for (var i = 1; i <= 10; i++) {
        var tr_string = '<tr>';
        for (var j = 5; j >= 1; j--) {
            var iiss = ""
            iiss = "05" + TwoNumber(i) + '0' + j
            if (arrdata.in_array(iiss) == true) {
                var Store_id = '05'+ TwoNumber(i)+'0'+j
                for (var k = 0; k<arrdetail.length; k++){
                    if (arrdetail[k].id == Store_id){
                        tr_string += '<td id="05' + TwoNumber(i) + '0' +j + '" onmousemove="stockdetail(id,arrdetail)" onmouseout="hidedetail()"><img src="../img/missile_green.png"  style="height:40%;height:35%"></td>'
                    }
                }
            }else {
                tr_string += '<td id="05' + TwoNumber(i) + '0' +j + '"><img src="../img/missile.png"  style="height:40%;height:35%"></td>'
            }
            // arrId.push({id:"05" + TwoNumber(i) + '0' + j})

        }
        tr_string += '</tr>'
        tab5 += tr_string;
    }
    $("#table_five").html(tab5);
    //右边的表格
    var tab6 = ''
    for (var i = 1; i <= 11; i++) {
        var tr_string = '<tr>';
        for (var j = 1; j <= 4; j++) {
            var iiss = ""
            iiss = "06" + TwoNumber(i) + '0' + j
            if (arrdata.in_array(iiss) == true) {
                var Store_id = '06'+ TwoNumber(i)+'0'+j
                for (var k = 0; k<arrdetail.length; k++){
                    if (arrdetail[k].id == Store_id){
                        tr_string += '<td id="06' + TwoNumber(i) + '0' +j + '" onmousemove="stockdetail(id,arrdetail)" onmouseout="hidedetail()"><img src="../img/missile_green.png"  style="height:40%;height:35%"></td>'
                    }
                }
            }else {
                tr_string += '<td id="06' + TwoNumber(i) + '0' +j + '"><img src="../img/missile.png"  style="height:40%;height:35%"></td>'
            }
            // arrId.push({id:"06" + TwoNumber(i) + '0' + j})
        }
        tr_string += '</tr>'
        tab6 += tr_string;
    }
    $("#table_six").html(tab6);
}


//鼠标移入显示产品详情信息框
function stockdetail(id,detaile_obj,obj){
   for (var i= 0; i<detaile_obj.length;i++){
       if (detaile_obj[i].id == id){
           var names = start(obj)
           $('#information').show()
           $("#information").css({"top":names[0],"left":names[1]});//设置提示div的位置
           $('#information').html(
               '<div>'+'产品名称：'+detaile_obj[i].name+'</div>'+
               '<div>'+'产品型号：'+detaile_obj[i].Model+'</div>'+
               '<div>'+'入库时间：'+detaile_obj[i].time+'</div>'+
               '<div>'+'库位：'+detaile_obj[i].storehouse+'</div>'
           )

       }

   }


}
function mousePos(e){
    var x,y;
    var e = e||window.event;
    return{x:e.clientX+document.body.scrollLeft+document.documentElement.scrollLeft,
        y:e.clientY+document.body.scrollTop+document.documentElement.scrollTop};
}
function start(e) {
        var mouse = mousePos(e);
        var left = mouse.x + 'px';
        var top = mouse.y + 'px';
        if (mouse.y + 400 > 900) {
            top = mouse.y - 500 + 'px';
        }
    var names=new Array(top,left);
    return names
}
//鼠标移出隐藏
function hidedetail() {
    $("#information").hide()
}