$().ready(function(){
    // drawCenterLeft()
    // drawCenterRight()
    // drawBottom('bottomItem1')
    // drawBottom('bottomItem2')
    // drawBottom('bottomItem3')
    // drawBottom('bottomItem4')
})

function drawCenterLeft(xArr,yArr){
    var myChart = echarts.init(document.getElementById('centerBox_left'));
    var option = {
        title: {
            text: '各类型产品占库位数'
        },
        xAxis: {
            type: 'category',
            data: xArr
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: yArr,
            type: 'bar',
            barWidth : 55,
            itemStyle: {
                normal: {
                    color: '#30A5FF', //改变折线点的颜色
                    label:{
                        show:true,
                        formatter:'{b}:  {c} '
                    }
                }
            }
        }]
    };
    myChart.setOption(option, true);
}

function drawCenterRight(data){
    var myChart = echarts.init(document.getElementById('centerBox_right'));
    var option = {
        title: {
            text: '产品库存比例',
            // subtext: '纯属虚构',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
        },
        series: [
            {
                name: '产品库存',
                type: 'pie',
                radius: '50%',
                data: data,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                itemStyle: {
                    normal:{
                        color:function(params) {
                            var colorList = [
                                '#30A5FF', '#B354FF', '#FFC901'
                            ];
                            return colorList[params.dataIndex]
                        },
                        label:{
                            show:true,
                            formatter:'{b}: {c} ({d}%)'
                        }
                    },
                    shadowBlur: 200,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                animationType: 'scale',
                animationEasing: 'elasticOut',
                animationDelay: function (idx) {
                    return Math.random() * 200;
                }
            }
        ]
    };
    myChart.setOption(option, true);
}

function  drawBottom(id,arr) {
    var myChart = echarts.init(document.getElementById(id));
    var option = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            top: '2%',
            left: 'left'
        },
        series: [
            {
                name: '',
                type: 'pie',
                radius: ['40%', '65%'],
                avoidLabelOverlap: false,
                itemStyle: {
                    borderRadius: 10,
                    borderColor: '#fff',
                    borderWidth: 2
                },
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: '16',
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: true
                },
                data: arr,
                itemStyle: {
                    normal:{
                        color:function(params) {
                            var colorList;
                            if(id=='bottomItem1'){
                                colorList = [
                                    '#B354FF', '#FFC901'
                                ];
                            }
                            if(id=='bottomItem2'){
                                colorList = [
                                    '#40DBD1', '#34B5FF'
                                ];
                            }
                            if(id=='bottomItem3'){
                                colorList = [
                                    '#EA1586', '#FFC901'
                                ];
                            }
                            if(id=='bottomItem4'){
                                colorList = [
                                    '#B354FF', '#40DBD1'
                                ];
                            }

                            return colorList[params.dataIndex]
                        },
                        label:{
                            show:true,
                            formatter:'{b}: {c}'
                        }
                    },
                    shadowBlur: 200,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                animationType: 'scale',
                animationEasing: 'elasticOut',
                animationDelay: function (idx) {
                    return Math.random() * 200;
                }
            }
        ]
    };
    myChart.setOption(option, true);
}