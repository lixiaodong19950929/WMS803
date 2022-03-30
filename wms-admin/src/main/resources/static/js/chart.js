<!--封装方法-->
//防止出现“There is a chart instance already initialized on the dom.”的警告
//在使用echarts发现需要及时对新建的myChart实例进行销毁,否则会出现上述警告
var _myChart={};
/**
 * 调用echart绘制统计图 chartId chart的div的id maxTas 统计图y轴最大值 minTas 统计图y轴最小值 tasArray
 * 统计图x轴数据数组 yearArray 统计图y轴数据数组 chartType 统计图图表类型：line是折线图，bar是柱状图
 * imageName：下载图片名称 axisName y轴坐标轴名称 markLineValue 标示线（常年值）
 */
function makeChart(chartId, tasArray, yearArray, chartType, imageName,yxisName,markLineValue,xname,maxTas,minTas) {
    $('#' + chartId).css({
        width : $('#' + chartId).width() + 'px',
        height : $('#' + chartId).height() + 'px'
    });

    var echartsWarp = document.getElementById(chartId);
    var chartCanvas = $("#"+chartId).html();
    if(chartCanvas.trim().length>0 && _myChart[chartId]!=null && _myChart[chartId]!="" && _myChart[chartId]!=undefined){
        _myChart[chartId].dispose();//销毁存在的实例
    }
    _myChart[chartId] = echarts.init(echartsWarp);

    var arrSeries = [];
    // 线性趋势
    var data = [];
    for (var i = 0; i < yearArray.length; i++) {
        data.push([Number(yearArray[i]),Number(tasArray[i])]);
    }
    var myRegression = ecStat.regression('linear', data);
    for(var i=0;i<myRegression.points.length;i++){
        var point=myRegression.points[i];
        point[1]=point[1].toFixed(2);
    }
    arrSeries.push({
            name : yxisName,
            type : chartType,
            barWidth:50, //柱子的宽度
            // color : '#ff0000',
            // markLine : {
            //     data :  [{
            //         name : '常年值',
            //         yAxis : markLineValue
            //     }]
            // },
            smooth : false,
            data : yearArray,
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                normal:{
                    color:function(params) {
                        //自定义颜色
                        var colorList = [
                            '#00C7DD'
                        ];
                        return colorList[params.dataIndex]
                    }
                }
            }

        }
        // ,{//线性变化趋势
        //     name: '线性变化',
        //     type: 'line',
        //     color:'#001fff',
        //     itemStyle:{
        //         normal:{
        //             lineStyle:{
        //                 width:2,
        //                 type:'dashed'
        //             }
        //         }
        //     },
        //     showSymbol: false,
        //     smooth:true,
        //     data: myRegression.points
        // }
        );
    var option = {
        title : {
            x : 'center',
            text: imageName,
            // textStyle : {
            //     fontSize : 12,
            //     fontWeight : 'bolder',
            //     color : '#151589'
            // },
            left:'left'
        },
        tooltip : {
            trigger : 'axis',
            axisPointer : {
                type : 'none',
            }
        },
        legend: {
            // y:4,
            // itemGap:20,
            // left:10,
            // selectedMode: 'multiple',
            // textStyle : {
            //     fontSize : 16,
            //     color : '#000000'
            // },
            orient: 'vertical',
            x: 'right',
            y: 'top',
            data:[yxisName]
        },
        // toolbox : {
        //     show : true,
        //     top : 0,
        //     right : 20,
        //     feature : {
        //         saveAsImage : {
        //             show : true,
        //             iconStyle : {
        //                 borderColor : '#dd8f56',
        //             },
        //             name : imageName
        //         }
        //     }
        // },
        grid: {show:'true',borderWidth:'0'}, //去除y轴的边框
        // grid : {
        //     show:true,
        //     left:64,
        //     top:27,
        //     right:50,
        //     bottom:45,
        //     // borderColor:'none'
        // },
        xAxis : {
            show : true,
            type : 'category',
            data:tasArray,
            // name: xname,
            nameLocation: 'middle',
            nameGap: 24,

            nameTextStyle:{
                fontSize:16
            },
            // axisTick : {
            //     inside : false
            // },

            axisTick:{       //x轴刻度线
                show:false
            },

            axisLabel : {
                textStyle : {
                    color : '#000000',
                    // fontSize: 14
                }
            },
            // splitLine : {
            //     show : false,// x轴线
            //
            // },
            axisLine : {
                lineStyle : {
                    color : '#ccc',
                    width : 1,
                    type : 'solid'
                }
            }
        },
        yAxis : {
            min : 0,
            max : maxTas,
            axisTick : {
                inside : false
            },
            axisLine:{       //y轴
                show:false

            },

            axisTick:{       //y轴刻度线
                show:false
            },
            // name: yxisName,
            // nameLocation: 'middle',
            // nameRotate: 90,
            // nameGap: 48,
            // axisLabel : {
            //     textStyle : {
            //         color : '#000000',
            //         fontSize: 16
            //     }
            // },
            // splitLine : {
            //     show : true,
            //     lineStyle : {
            //         color : '#eeeeee'
            //     }
            // },
            type : 'value',
            // axisLine : {
            //     lineStyle : {
            //         color : '#000000',
            //         width : 2,
            //         type : 'solid'
            //     }
            // }
        },
        series : arrSeries
    };
    // option = {
    //     xAxis: {
    //         type: 'category',
    //         data: tasArray,
    //         splitLine : {
    //             show : true,
    //             lineStyle : {
    //                 color : '#eeeeee'
    //             }
    //         },
    //         axisLine : {
    //             lineStyle : {
    //                 color : '#000000',
    //                 width : 2,
    //                 type : 'solid'
    //             }
    //         }
    //     },
    //     yAxis: {
    //         type: 'value',
    //     },
    //     series: [{
    //         data: yearArray,
    //         type: 'bar'
    //     }],
    //     toolbox : {
    //         show: true,
    //         top: 0,
    //         right: 20,
    //         feature: {
    //             saveAsImage: {
    //                 show: true,
    //                 iconStyle: {
    //                     borderColor: '#dd8f56',
    //                 },
    //                 name: imageName
    //             }
    //         }
    //     }
    // };



    _myChart[chartId].setOption(option);
}
function makechartbing(chartId,yiyong,weiyong){

    option = {
        title: {
            text: '库存利用率',
            left: '0',
            top: 'top',
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
            orient: 'vertical',
            x: 'right',
            y: 'top',
            data: ['已用', '未用']
        },
        series: [
            {
                name: '库位数',
                type: 'pie',
                selectedMode: 'single',
                radius: ['40%', '65%'],// 空心饼图，实心饼图【0，’50%‘】
                avoidLabelOverlap: false,
                // label: {
                //     position: 'inner'
                // },
                // labelLine: {
                //     show: false
                // },
                data: [
                    {value: yiyong, name: '已用', selected: true},
                    {value: weiyong, name: '未用'},

                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    },
                    normal:{
                        color:function(params) {
                            //自定义颜色
                            var colorList = [
                                '#00C7DD','#C45CFF'
                            ];
                            return colorList[params.dataIndex]
                        }
                    }
                }
            },
        ]
    };
    echarts.init(document.getElementById(chartId)).setOption(option)
}

function makeChartchong(chartId,datain,dataout,dataMouthx) {
    var chart_c1 = echarts.init(document.getElementById(chartId));//指定的id要有高度和宽度
    // var dataMoney = [0, 20, 40, 60, 80, 100, 120, 140, 160];//y轴

    // var dataMouth = ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'];//x轴

    //显示数据，可修改
    // var data1 = [22, 24, 38, 43, 59, 25, 49, 34, 44, 34, 22, 46];
    // var data2 = [35, 46, 43, 59, 60, 45, 53, 42, 56, 45, 36, 59];
    //总计
    var data3 = function() {
        var datas = [];
        for (var i = 0; i < datain.length; i++) {

            datas.push(datain[i] + dataout[i]);
        }
        return datas;
    }();
    option = {
        title: {
            text: '近十次出入库',
            left: '0',
            top: 'top',
        },
        tooltip : {
            trigger: 'axis',
            axisPointer : {
                type : 'none',
            }
        },
        legend: {
            orient: 'vertical',
            x: 'right',
            y: 'top',
            data:['入库','出库']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : dataMouthx,
                axisTick:{       //刻度线
                    show:false
                },
                axisLine:{
                    lineStyle : {
                        color : '#ccc',
                        width : 1,
                        type : 'solid',
                    }
                },
                axisLabel:{
                    textStyle:{color:'#000'}
                }
            }
        ],
        yAxis : [
            {
                type : 'value',
                // data : dataMoney //可省略，只要type : 'value',会自适应数据设置Y轴
                axisLine:{       //y轴
                    show:false

                },

                axisTick:{       //y轴刻度线
                    show:false
                },
            }
        ],
        series : [
            {
                name:'入库',
                type:'bar',
                stack:'sum',
                itemStyle:{
                    normal:{
                        label: {
                            show: true,//是否展示
                        },
                        color:'#00C7DD'
                    }
                },
                data:datain
            },
            {
                name:'出库',
                type:'bar',
                stack:'sum',
                barWidth : 30,
                itemStyle:{
                    normal:{
                        label: {
                            show: true,//是否展示
                        },
                        color:'#006AFF'
                    }
                },
                data:dataout
            },
            // {
            //     name: '总计',
            //     type: 'bar',
            //     stack: 'sum',
            //     label: {
            //         normal: {
            //             offset:['50', '80'],
            //             show: true,
            //             position: 'insideBottom',
            //             formatter:'{c}',
            //             textStyle:{ color:'#000' }
            //         }
            //     },
            //     itemStyle:{
            //         normal:{
            //             color:'rgba(128, 128, 128, 0)'
            //         }
            //     },
            //     data: data3
            // }

        ]
    };

    chart_c1.setOption(option);
}


// $(function(){
//     var tasArray =[45,67,90,20,10,24,15,81];
//     var yearArray = [1994,1995,1996,1997,1998,1999,2000,2001];
//     var maxTas = Math.max.apply(Math,tasArray);//y轴最大值
//     var minTas = Math.min.apply(Math,tasArray);//y轴最小值
//     var dataNameLine = "河北省指数变化折线图";
//     var dataNameBar = "河北省指数变化柱状图";
//     // makeChart("zheXianCon",maxTas,minTas,tasArray,yearArray,"line",'yaosu',dataNameLine,false,false);
//     makeChart("zhuZhuangCon",maxTas,minTas,tasArray,yearArray,"bar",'yaosu',dataNameBar,false,false);
// })



