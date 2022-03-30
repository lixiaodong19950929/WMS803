
// echarts 饼图封装

//饼图一，未使用
var not_used ='';
//饼图一，已使用
var already_used = ''
//并图二 已完成
var finishedQuantity = ''
//饼图二 未开始
var notStartQuantity = ''
//饼图二 正在执行
var runningQuantity = ''
var data1 = []
var data11 = []
    $().ready(function(){
    pic()
})

 function pic () {
    $.ajax({
    cache: true,
    url:"/wms/kanban/useInOutMovePie",
    type: "POST",
    success: function (data) {
        $("#intoQuantity").html(data.inOutMovePie.intoQuantity)
        $("#outQuantity").html(data.inOutMovePie.outQuantity)
        $("#moveQuantity").html(data.inOutMovePie.moveQuantity)
        not_used = data.useCasePie.stockQuantity
        already_used = data.useCasePie.outQuantity
        finishedQuantity = data.useCasePie.finishedQuantity
        notStartQuantity = data.useCasePie.notStartQuantity
        runningQuantity = data.useCasePie.notStartQuantity
        data1=[
            {value: not_used, name: '已使用'},
            {value: already_used, name:'未使用'
            }]
        data11=[
            {value: notStartQuantity, name: '入库任务'},
            {value: finishedQuantity, name:'移库任务'},
            {value: runningQuantity, name:'出库任务'}]
        drawPieChart('Inventory', 1,'库存量',['#6C00FF','#325CFE'],data1,data2);
        drawPieChart('Task', 1,'作业任务',['#6C00FF','#325CFE','#449740'],data11,data22);
    }
})
 }

var data2=['直接访问','邮件营销']
var data22=['已使用','未使用','disange']
function drawPieChart(id, type, name,color1,data1,data2) {
    var myChart = echarts.init(document.getElementById(id));
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        graphic: {
            type: 'text',
            left: 'center',
            top: 'center',
            z: 2,
            zlevel: 100,
            style: {
                fill:'#087ADD',
                text: name,
                width: 100,
                height: 30,
                font: '1.1vw Microsoft YaHei',
            }
        },
        legend: {
            show: false,
            x: 'left',
            data:data2,
        },
        color: color1,
        series: [
            {
                name: name,
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    // emphasis: {
                    //     show: true,
                    //     textStyle: {
                    //         fontSize: '30',
                    //         fontWeight: 'bold'
                    //     }
                    // }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: data1,
            }
        ]
    };;

    if (option && typeof option === "object") {
        myChart.setOption(option, true);
        setTimeout(function (){
            window.onresize = function () {
                myChart.resize();
            }
        },200)
    }
}
