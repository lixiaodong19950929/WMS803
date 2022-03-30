var inter = null;
$("#logBoxBtn").click(function(){
    if($("#logBoxWord").text()=="任务日志"){
        $("#logBox").css("width","500px")
        $("#logBoxWord").text('折叠日志')
        for(var i=0;i<60;i++){
            $("#logUL").append("<li style='margin: 8px 0;font-size: 16px'>这是一条日志</li>")
        }
        document.getElementById("logULBox").scrollTop = document.getElementById("logULBox").scrollHeight
        ceshi()
        inter = setInterval(function(){
            $("#logUL").append("<li style='margin: 8px 0;font-size: 16px'>这是循环插入的日志~~~~~~~~~~~</li>")
            document.getElementById("logULBox").scrollTop = document.getElementById("logULBox").scrollHeight
        },1000)
    }else{
        $("#logBox").css("width",0)
        $("#logBoxWord").text('任务日志')
        clearInterval(inter)
    }

})

function ceshi(val){
    var config = {
        url: ctx + "warehouse/storeio/selectBarcodeByTaskcode",
        type: "post",
        dataType: "json",
        data: {
            taskcode:1
        },
        beforeSend: function () {
            // $.modal.loading("正在处理中，请稍后...");

        },
        success: function(result) {
            // if (typeof executeCallback == "function") {
            //     executeCallback(result,val.split(',')[1],val.split(',')[0],val.split(',')[2]);
            // }
            // $.operate.ajaxSuccess(result);
        }
    };
    $.ajax(config)
}
