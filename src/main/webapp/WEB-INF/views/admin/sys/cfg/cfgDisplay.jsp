<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE HTML>
<head>
<title>系统配置项</title>
<%@include file="/WEB-INF/views/admin/css_Script.jsp" %>
<script type="text/javascript">
function mergeCfg(){
	if(!$("#sf").valid()){
	  	$.messager.alert("消息", "参数错误！", "error");
 		return false;
	}
	$.messager.confirm("操作提示", "${not empty cfg.cfgId ? '你确定要更新配置项信息吗？':'你确定要新增该配置项吗？'}", function (flag) {
	    if (flag) {
	        $.myAjax.excuteAjaxMask({
				url:"${base}/admin/sys/cfg/mergeCfg.do",
				reqData:$("#sf").serialize(),
				dataType: "json",
				successfn: function(data){
				   if(data.status < 0){
				         if(data.status == -1){
				         	$.messager.alert("消息", "参数错误！", "error");
 							return false;
				         }else if(data.status  ==-2){
				         	$.messager.alert("消息", "重复的配置项！", "error");
 							return false;
				         }
				    }
				  $.messager.alert("消息", "${not empty cfg.cfgId ? '修改成功!':'新增成功!'}！", "info",function (){
				  	window.location.href="${base}/admin/sys/cfg/cfgDisplay.do?cfgId="+data.cfgId;
				  });
				}
			});
	    }
	});
}
$(document).ready(function (){
	$("#sf").validate({  rules: {"cfgName": {required:true,maxlength:30,minlength:3},"cfgKey":{required:true,maxlength: 50,minlength:3},"cfgValue":{required:true,maxlength: 700},"cfgDesc":{required:true,maxlength: 30},"cfgSn":{required:true,number: true}}});
});
</script>
</head>
<body>
<h3 class="editContainerTitle">系统配置项_${not empty cfg.cfgId ? '修改':'新增'}</h3>
<div class="editContainer"  style="width: 80%">
    <form  method="post" id="sf">
    	<input type="hidden" name="cfgId"  value="${ cfg.cfgId }"/>
        <div class="editRow">
            	<label class="edit_lable"><em>*</em>配置项Name:</label>
            	<input type="text" name="cfgName"  value="${cfg.cfgName}" placeholder="配置项名称" class="eidt_input">
            	<p class="edit_input_tip"></p>
        </div>
        <div class="editRow">
            	<label class="edit_lable"><em>*</em>配置项Key:</label>
            	<input type="text" name="cfgKey"  value="${cfg.cfgKey}"  placeholder="配置项Key" class="eidt_input">
            	<p class="edit_input_tip"></p>
        </div>
        <div class="editRow" >
            <label class="edit_lable" ><em>*</em>配置项Value:</label>
            	<textarea name="cfgValue"  class="eidt_input"><c:out value='${cfg.cfgValue}'/></textarea>
            	<p class="edit_input_tip"></p>
        </div>
        <div class="editRow">
            	<label class="edit_lable"><em>*</em>配置项Desc:</label>
            	<input type="text" name="cfgDesc"  value="<c:out value='${cfg.cfgDesc}'/>"  placeholder="描述" class="eidt_input">
            	<p class="edit_input_tip"></p>
        </div>
          <div class="editRow">
            	<label class="edit_lable"><em>*</em>配置项排序:</label>
            	<input type="text" name="cfgSn"  value="${ empty cfg.cfgSn ?0:cfg.cfgSn}"  placeholder="排序序号" class="eidt_input">
            	<p class="edit_input_tip"></p>
        </div>
        <div class="editRow">
           <div style="text-align: center;">
           		<input type="button"  value="提交"   class="orangeBtn" onclick="mergeCfg()"/>
        		<input type="button"  value="关闭"   class="grayBtn" onclick="javascript:parent.colseTabsByTitle('系统配置项编辑')"/>
        	</div>
        </div>
    </form>
</div>
</body>
</html>