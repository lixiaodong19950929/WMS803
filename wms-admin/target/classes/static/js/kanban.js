
//看板

// 中一

$.ajax({
    cache: true,
    url:"/wms/kanban/kbStoreIoList",
    type: "POST",
    success: function (data) {
        // console.log('中一少库位',data)
        var left_list = ''
        for (var k = 0; k < data.length; k++) {
            var taskType = ''
            // if (data[k].taskType == 1){
            //     taskType = "<td style='color:#46992F;margin-left:20px;'>"+ "入库"+"</td>"
            // }else if(data[k].taskType == 2){
            //     taskType = "<td style='color:#087ADD;margin-left:20px;'>"+ "出库"+"</td>"
            // }
            left_list = "<tr style='color: #fff; height:36px;'>"+
            "<td style='width: 25%;'>"+data[k].storehousename+"</td>"+
            "<td style='width: 25%;'>"+data[k].productname+"</td>"+
            "<td style='width: 25%;'>"+data[k].trayCode+"</td>"+
            "<td style='width: 25%;'>"+data[k].otherProductCode+"</td>"+
            "</tr>"
            $('.tbl-body tbody').append(left_list);
            $('.tbl-header tbody').append(left_list);
        }
        if(data.length > 8){
            $('.tbl-body tbody').html($('.tbl-body tbody').html()+$('.tbl-body tbody').html());
            $('.tbl-body').css('top', '0');
            var tblTop = 0;
            var speedhq = 50; // 数值越大越慢
            var outerHeight = $('.tbl-body tbody').find("tr").outerHeight();
            function Marqueehq(){
                if(tblTop <= -outerHeight*data.length){
                    tblTop = 0;
                } else {
                    tblTop -= 1;
                }
                $('.tbl-body').css('top', tblTop+'px');
            }

            MyMarhq = setInterval(Marqueehq,speedhq);

            // 鼠标移上去取消事件
            $(".tbl-header tbody").hover(function (){
                clearInterval(MyMarhq);
            },function (){
                clearInterval(MyMarhq);
                MyMarhq = setInterval(Marqueehq,speedhq);
            })

        }
        // $("#left_bottom").html(left_list)
    }
})

// 中二
$.ajax({
    cache: true,
    url:"/wms/kanban/deviceList",
    type: "POST",
    success: function (data) {
        // var deviceList = ''
        // var taskState= ''
        // for (var i = 0; i<data.length;i++) {
        //     if (data[i].deviceState == 0){taskState = "<td style='color:#087ADD;width: 30%;'>"+ "待机"+"</td>"
        //     }else if(data[i].deviceState == 1){taskState = "<td style='color:#46992F;width: 30%;'>"+ "执行中"+"</td>"
        //     }else if (data[i].deviceState == 2) {taskState = "<td style='color:#ccc;width: 30%;'>"+ "停机"+"</td>"
        //     }else if(data[i].deviceState == -1){taskState = "<td style='color:red;width: 30%;'>"+ "故障"+"</td>"}
        //     deviceList += "<tr style='height:26px;'>"+
        //         "<td>"+data[i].deviceName+"</td>"+
        //             taskState +
        //         "<td>"+data[i].createDate+"</td>"+
        //     "</tr>"
        // }
        var list = ''
        var ruchuku = ''
        var taskState= ''
        for (var j = 0;j < data.length; j++) {
            var taskstate="";
            if (data[j].taskstate==1){
                taskstate="执行中";
            }else if(data[j].taskstate==0){
                taskstate="未执行";
            }
            list ="<tr style='height:26px;'>"+
                "<td style='width: 25%'>"+data[j].taskname+"</td>"+
                "<td style='width: 25%;'>"+data[j].quantity+"</td>"+
                "<td style='width: 25%'>"+taskstate+"</td>"+
                "<td>"+data[j].maker+"</td>"+
                "</tr>"
            $('.center-body tbody').append(list);
            $('.center-header tbody').append(list);
        }
        if(data.length > 8){
            $('.center-body tbody').html($('.center-body tbody').html()+$('.center-body tbody').html());
            $('.center-body').css('top', '0');
            var tblTop = 0;
            var speedhq = 50; // 数值越大越慢
            var outerHeight = $('.center-body tbody').find("tr").outerHeight();
            function Marqueehq_center(){
                if(tblTop <= -outerHeight*data.length){
                    tblTop = 0;
                } else {
                    tblTop -= 1;
                }
                $('.center-body').css('top', tblTop+'px');
            }

            MyMarhq_center = setInterval(Marqueehq_center,speedhq);

            // 鼠标移上去取消事件
            $(".center-header tbody").hover(function (){
                clearInterval(MyMarhq_center);
            },function (){
                clearInterval(MyMarhq_center);
                MyMarhq_center = setInterval(Marqueehq_center,speedhq);
            })

        }
        // $("#right_top").html(
        //     list
        // )
    }
})

//左二
$.ajax({
    cache: true,
    url:"/wms/kanban/kbTaskStackerList",
    type: "POST",
    success: function (data) {

        var list = ''
        var ruchuku = ''
        var taskState= ''
        for (var j = 0;j < data.length; j++) {
            var taskstate="";
            if (data[j].taskstate==1){
                taskstate="执行中";
            }else if(data[j].taskstate==0){
                taskstate="未执行";
            }
            list ="<tr style='height:26px;'>"+
                "<td style='width:25%'>"+data[j].taskname+"</td>"+
                "<td style='width:25%'>"+data[j].quantity+"</td>"+
                "<td style='width:25%'>"+taskstate+"</td>"+
                "<td style='width:25%'>"+data[j].maker+"</td>"+
                "</tr>"
            $('.caiji-body tbody').append(list);
            $('.caiji-header tbody').append(list);
        }
        if(data.length > 8){
            $('.caiji-body tbody').html($('.caiji-body tbody').html()+$('.caiji-body tbody').html());
            $('.caiji-body').css('top', '0');
            var tblTop = 0;
            var speedhq = 50; // 数值越大越慢
            var outerHeight = $('.caiji-body tbody').find("tr").outerHeight();
            function Marqueehqs(){
                if(tblTop <= -outerHeight*data.length){
                    tblTop = 0;
                } else {
                    tblTop -= 1;
                }
                $('.caiji-body').css('top', tblTop+'px');
            }

            MyMarhqs = setInterval(Marqueehqs,speedhq);

            // 鼠标移上去取消事件
            $(".caiji-header tbody").hover(function (){
                clearInterval(MyMarhqs);
            },function (){
                clearInterval(MyMarhqs);
                MyMarhqs = setInterval(Marqueehqs,speedhq);
            })

        }

    }
})
//
// 右二
$.ajax({
    cache: true,
    url:"/wms/kanban/getSensorList",
    type: "POST",
    success: function (data) {
        // console.log('右二',data)
        // $('#qingjiao_zheng').html(data[0].formalNumber)
        // $('#qingjiao_yi').html(data[0].exceptionNumber)
        // $('#guangdian_zheng').html(data[1].formalNumber)
        // $('#guangdian_yi').html(data[1].exceptionNumber)
        var list = ''
        var ruchuku = ''
        var taskState= ''
        for (var j = 0;j < data.length; j++) {
            var taskstate="";
            if (data[j].taskstate==1){
                taskstate="执行中";
            }else if(data[j].taskstate==0){
                taskstate="未执行";
            }
            list ="<tr style='height:26px;'>"+
                "<td style='width: 25%'>"+data[j].taskname+"</td>"+
                "<td style='width: 25%;'>"+data[j].quantity+"</td>"+
                "<td style='width: 25%'>"+taskstate+"</td>"+
                "<td>"+data[j].maker+"</td>"+
                "</tr>"
            $('.right-body tbody').append(list);
            $('.right-header tbody').append(list);
        }
        if(data.length > 8){
            $('.right-body tbody').html($('.right-body tbody').html()+$('.right-body tbody').html());
            $('.right-body').css('top', '0');
            var tblTop = 0;
            var speedhq = 50; // 数值越大越慢
            var outerHeight = $('.right-body tbody').find("tr").outerHeight();
            function Marqueehqs_right(){
                if(tblTop <= -outerHeight*data.length){
                    tblTop = 0;
                } else {
                    tblTop -= 1;
                }
                $('.right-body').css('top', tblTop+'px');
            }

            MyMarhqs_right = setInterval(Marqueehqs_right,speedhq);

            // 鼠标移上去取消事件
            $(".right-header tbody").hover(function (){
                clearInterval(MyMarhqs_right);
            },function (){
                clearInterval(MyMarhqs_right);
                MyMarhqs_right = setInterval(Marqueehqs_right,speedhq);
            })

        }

    }
})

function time () {
    var myDate = new Date();
    $('.title_time').html(myDate.getFullYear() + '-' + addzero((myDate.getMonth()+1)) + '-' + addzero(myDate.getDate()) + ' ' +
        addzero(myDate.getHours()) + ':' + addzero(myDate.getMinutes()) + ':' + addzero(myDate.getSeconds())
    )
}
setInterval(time, 1000)

function addzero(date) {
    if (date < 10){
        return '0'+ date
    } else {
        return date
    }

}
