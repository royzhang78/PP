function httpCall(serviceName, callPara, callBack){
    var param = {
        serviceName: serviceName,
        callPara: callPara
    };
    $.ajax({
        url: (typeof httpUrl == 'undefined') ? "httpService.do" : httpUrl,
        data: jsonToString(param),
        type: "POST",
        dataType: "text",
        async: false,
        timeout: 30000,
        processData: false,
        success: callBack
    });
}

function jsonToString(obj){
    var THIS = this;
    switch(typeof(obj)){
        case 'string':
            return '"' + obj.replace(/(["\\])/g, '\\$1') + '"';
        case 'array':
            return '[' + obj.map(THIS.jsonToString).join(',') + ']';
        case 'object':
             if(obj instanceof Array){
                var strArr = [];
                var len = obj.length;
                for(var i=0; i<len; i++){
                    strArr.push(THIS.jsonToString(obj[i]));
                }
                return '[' + strArr.join(',') + ']';
            }else if(obj==null){
                return 'null';

            }else{
                var string = [];
                for (var property in obj) string.push(THIS.jsonToString(property) + ':' + THIS.jsonToString(obj[property]));
                return '{' + string.join(',') + '}';
            }
        case 'number':
            return obj;
        case false:
            return obj;
    }
}

function nextStep() {
    $("#retContent").html(LNG.HINT["loading"]);
    $("#confirmBtn").hide();
    $("#popupDialog").popup("open", {});
    //$('#startForm').validate(formSetting).data('validate');
    if(param){
        param.tmcc14digits = $("#tmcc14digits").val();
    }
    httpCall("thailandCashCardJson", param, function(result){
        var json = eval('(' + result + ')');
        if (json.returnCode && json.returnCode == "0" && json.returnObjs) {
            $("#mcode").val(json.returnObjs.mcode);
            $("#inv_no").val(json.returnObjs.inv_no);
            $("#product_name").val(json.returnObjs.product_name);
            $("#amount").val(json.returnObjs.amount);
            $("#pay_type").val(json.returnObjs.pay_type);
            $("#response_url").val(json.returnObjs.response_url);
            $("#currency").val(json.returnObjs.currency);
            $("#language").val(json.returnObjs.language);
            $("#sof").val(json.returnObjs.sof);
            
            $('#startForm').attr("action", json.returnObjs.action).submit();
        } else {
            var msg = LNG.ERROR_CODE[json.returnCode];
            if(msg){
                $("#retContent").html(msg);
            } else {
                $("#retContent").html(result);
            }
            $("#confirmBtn").show();
        }
    });
}

var formSetting = {
    singleError : function($field, rules){
        $field.parent().prev().addClass('error');
    },
    singleSuccess : function($field, rules){
        $field.parent().prev().removeClass('error');
    },
    overallSuccess : function(){
        $("#retContent").html(LNG.HINT["loading"]);
        $("#confirmBtn").hide();
        $("#popupDialog").popup("open", {});
        
        var param = {
            gameName: $("#gameName").val(),
            uId: $("#uId").val(),
            gameRoleId: $("#gameRoleId").val(),
            locale: locale
        };
        
        httpCall("payStart", param, function(result){
            var json = eval('(' + result + ')');
            if (json.returnCode && json.returnCode == "0" 
                && json.returnObjs && json.returnObjs.redirectUrl) {
                location.href = json.returnObjs.redirectUrl;
            } else {
                var msg = LNG.ERROR_CODE[json.returnCode];
                if(msg){
                    $("#retContent").html(msg);
                } else {
                    $("#retContent").html(result);
                }
                $("#confirmBtn").show();
            }
        });
    },
    overallError : function($form, fields){
        $(fields).get(0).focus();
    },
    autoDetect : true,
    debug : true,
    visibleOnly : true
};
