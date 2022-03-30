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
//表格循环
var arrdata = []
window.onload = function makeForm() {
    arrdata = []
    $.ajax({
        cache:true,
        type: 'POST',
        // data:{whcode},
        async:false,
        url: '/infoquery/querystockstatus/list',
        success: function (data) {
            console.log("data",data)
            for (var k = 0; k<data.length;k++){
                arrdata.push(data[k].storehousecode)
            }
        }
    })
    console.log("arrt",arrdata)
    tableFor("","","","")
    }
function searchTables(whname,storehouseline,storehousecolum,storehouselayer) {
    arrdata = []
    $.ajax({
        cache:true,
        type: 'POST',
        data:{
            "whname":whname,
            "storehouseline":storehouseline,
            "storehousecolum":storehousecolum,
            "storehouselayer":storehouselayer,
        },
        async:false,
        url: '/infoquery/querystockstatus/list',
        success: function (data) {
            console.log("d",data)
            for (var k = 0; k<data.length;k++){
                arrdata.push(data[k].storehousecode)
                console.log("d",data[k].storehousecode)
            }
        }
    })
    tableFor(whname,storehouseline,storehousecolum,storehouselayer)
}
function tableFor(whname,storehouseline,storehousecolum,storehouselayer) {
    //左边的表格
    var tab1 = '';
    for (var i = 1; i <= 11; i++) {
        // if (storehouseline=="01") {
        //     if (i<=9){
        //         var num = "0"+i
        //     }else{
        //         var num = i
        //     }
        //     if (storehousecolum == num) {
        //         var tr_string = '<tr style="border: red;">';
        //     } else {
        //         var tr_string = '<tr>';
        //     }
        // }else {
        //     var tr_string = '<tr>';
        // }
        var tr_string = '<tr>';
        for (var j = 4; j >= 1; j--) {
            // console.log("边的表格",arrdata.in_array(iiss),typeof (iiss))
            var iiss = ""
            iiss = "01" + TwoNumber(i) + '0' + j
            if (arrdata.in_array(iiss) == true) {
                tr_string += '<td id="01' + TwoNumber(i) + '0' +j + '"><img src="../img/missile_green.png"  style="height:40%;height:35%"></td>'
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
            // console.log("中间第一个表格",arrdata.in_array("02" + TwoNumber(i) + '0' + j))
            var iiss = ""
            iiss = "02" + TwoNumber(i) + '0' + j
            if (arrdata.in_array(iiss) == true) {
                tr_string += '<td id="02' + TwoNumber(i) + '0' +j + '"><img src="../img/missile_green.png"  style="height:40%;height:35%"></td>'
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
            // console.log("中间第二个表格",arrdata.in_array("03" + TwoNumber(i) + '0' + j))
            var iiss = ""
            iiss = "03" + TwoNumber(i) + '0' + j
            if (arrdata.in_array(iiss) == true) {
                tr_string += '<td id="03' + TwoNumber(i) + '0' +j + '"><img src="../img/missile_green.png"  style="height:40%;height:35%"></td>'
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
            // console.log("中间第三个表格",arrdata.in_array("04" + TwoNumber(i) + '0' + j))
            var iiss = ""
            iiss = "04" + TwoNumber(i) + '0' + j
            if (arrdata.in_array(iiss) == true) {
                tr_string += '<td id="04' + TwoNumber(i) + '0' +j + '"><img src="../img/missile_green.png"  style="height:40%;height:35%"></td>'
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
            // console.log("中间第四个表格",arrdata.in_array("05" + TwoNumber(i) + '0' + j))
            var iiss = ""
            iiss = "05" + TwoNumber(i) + '0' + j
            if (arrdata.in_array(iiss) == true) {
                tr_string += '<td id="05' + TwoNumber(i) + '0' +j + '"><img src="../img/missile_green.png"  style="height:40%;height:35%"></td>'
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
            // console.log("右边的表格",arrdata.in_array("06" + TwoNumber(i) + '0' + j))
            var iiss = ""
            iiss = "06" + TwoNumber(i) + '0' + j
            if (arrdata.in_array(iiss) == true) {
                tr_string += '<td id="06' + TwoNumber(i) + '0' +j + '"><img src="../img/missile_green.png"  style="height:40%;height:35%"></td>'
            }else {
                tr_string += '<td id="06' + TwoNumber(i) + '0' +j + '"><img src="../img/missile.png"  style="height:40%;height:35%"></td>'
            }
            // arrId.push({id:"06" + TwoNumber(i) + '0' + j})
        }
        tr_string += '</tr>'
        tab6 += tr_string;
    }
    $("#table_six").html(tab6);
    // console.log('数组id',arrId)
}



//调用后台接口
// $.ajax({
//     cache:true,
//     type: 'POST',
//     // data:{whcode},
//     url: '/infoquery/querystockstatus/list',
//     success: function (data) {
//        for (var i = 0; i < arrId.length;i++){
//            for (var j = 0; j<data.length;j++){
//                if (data[j].storehousecode == arrId[i].id){
//                    console.log('相同的id',arrId[i].id)
//                }
//            }
//
//        }
//         console.log('库位管理',data)
//
//     }
// })