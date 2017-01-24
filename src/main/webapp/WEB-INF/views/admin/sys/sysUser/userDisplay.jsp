<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE HTML>
<head>
<title>系统用户信息</title>
<%@include file="/WEB-INF/views/admin/css_Script.jsp" %>
<script type="text/javascript">
function mergeSysUser(){
	if(!$("#sf").valid()){
	  	$.messager.alert("消息", "参数错误！", "error");
 		return false;
	}
	$.messager.confirm("操作提示", "${not empty sysUser.suId ? '你确定要更新用户信息？':'你确定要新增该系统用户？'}", function (flag) {
	    if (flag) {
	        $.myAjax.excuteAjaxMask({
				url:"${base}/admin/sys/user/mergeUser.do",
				reqData:$("#sf").serialize(),
				dataType: "json",
				successfn: function(data){
				    if(data.status < 0){
				         if(data.status == -1){
				             $.messager.alert("消息", "参数错误！", "error");
 							return false;
				         }else if(data.status  ==-2){
				         	$.messager.alert("消息", "登录名已存在！", "error");
 							return false;
				         }
				    }
				  	$.messager.alert("消息", "${not empty sysUser.suId ? '修改成功!':'新增成功!'}！", "info",function (){
						window.location.href="${base}/admin/sys/user/userDisplay.do?suId="+data.suId;
					});
				}
			});
	    }
	});
}
$(document).ready(function (){
    $("input[name='status']").each(function (){ if(this.value == "${sysUser.status}"){ $(this).attr("checked","checked");}else{ $(this).removeAttr("checked");}});
	$("#sf").validate({  rules: {"loginName": {required:true,maxlength: 50,minlength:3},"realName": {required:true,maxlength: 120,minlength:2}}});
});
</script>
</head>
<body>
<h3 class="editContainerTitle">${not empty sysUser.suId ? '系统用户信息修改':'添加新用户'}</h3>
<div class="editContainer"  style="width: 70%">
    <form method="post" id="sf">
    	<c:if test="${not empty sysUser.suId }"><input type="hidden" name="suId" value="${sysUser.suId }"/></c:if>
        <div class="editRow">
            	<label class="edit_lable"><em>*</em>登录名:</label>
            	<input type="text" name="loginName"  value="${sysUser.loginName}" placeholder="请输入登录名(唯一)" class="eidt_input">
            	<p class="edit_input_tip"></p>
        </div>
        <div class="editRow">
            	<label class="edit_lable"><em>*</em>真实姓名:</label>
            	<input type="text" name="realName"  value="${sysUser.realName}"  placeholder="请输入真实姓名" id="" class="eidt_input">
            	<p class="edit_input_tip"></p>
        </div>
        <c:if test="${not empty sysUser.suId }">
        <div class="editRow">
            	<label class="edit_lable"><em>*</em>密码:</label>
            	<input type="text" name="loginPwd"  value="${sysUser.loginPwd}"  class="eidt_input" disabled="disabled" readonly="readonly">
            	<p class="edit_input_tip"></p>
        </div>
        </c:if>
         <div class="editRow">
            <label class="edit_lable" >状态:</label>
            <div class="edit_choicebox">
                <div class="edit_choicebox_block"><label><input type="radio" name="status" value="0">有效</label><label><input type="radio" name="status" value="-1">无效</label></div>
            </div>
        </div>
        <div class="editRow">
           <div style="text-align: center;">
           		<input type="button"  value="提交"   class="orangeBtn" onclick="mergeSysUser()"/>
           		<c:if test="${ empty sysUser.suId }"><input type="button"  value="关闭"   class="grayBtn" onclick="javascript:parent.colseTabsByTitle('用户信息')"/></c:if>
           		<c:if test="${ not  empty sysUser.suId }"><input type="button"  value="关闭"   class="grayBtn" onclick="javascript:parent.colseTabsByTitle('用户信息')"/></c:if>
        	</div>
        </div>
    </form>
</div>
</body>
</html>