/*****************************************************************
jQuery Ajax封装类,主要用于全局AJAX异常时提示,如登录超时或权限不足等   
*****************************************************************/
(function($) {
$.myAjax = {
	 excuteAjaxMask:function (options){
		    var async = (options.async==null || options.async=="" || typeof(options.async)=="undefined")? "true" : options.async;
		    var method = (options.method==null || options.method=="" || typeof(options.method)=="undefined")? "post" : options.method;
		    var  dataType = (options.dataType==null || options.dataType=="" || typeof(options.dataType)=="undefined")? "json" : options.dataType;
		    var reqData = (options.reqData==null || options.reqData=="" || typeof(options.reqData)=="undefined")? "": options.reqData;
		    var isMask = (options.isMask==null || options.isMask=="" || typeof(options.isMask)=="undefined")? "true" : options.isMask;
			if(isMask){$.messager.progress({title:'正在处理数据,请等待....',text:''});}
		    $.ajax({
		         type: method,async: async,data: reqData,url: options.url,dataType: "html",
		         success: function(resData){
		        	 if(isMask){$.messager.progress('close');}
		        	if(resData.indexOf("对不起，您所访问的页面暂时不能访问……") !=-1){
		        		 window.document.write(resData);
		        		 return false;
		        	}else if(resData.indexOf("illegalPermission.jsp") !=-1){
		            	$.messager.alert("消息", "对不起,你没有对应的权限!", "error");
		            }else if(resData.indexOf("\\u8bf7\\u5148\\u767b\\u5f55!") !=-1){
		            	$.messager.alert("消息", "对不起,登录超时,请重新登录!", "error",function (){
		            		window.document.write(resData);
		            	});
		            }else{
			        	 if(dataType == "json"){
			        		 options.successfn($.parseJSON(resData));
			        	 }else{
			        		 options.successfn(resData);
			        	 }
		            }
		         },
		         error: function(XMLHttpRequest, textStatus, errorThrown){
		        	 alert(textStatus);
		        	 if(isMask){$.messager.progress('close');}
		        	  $.messager.alert("消息", "请求异常!"+textStatus, "error");
		        	  window.document.write(XMLHttpRequest.responseText);
		         }
		     });
	}
}
})(jQuery);