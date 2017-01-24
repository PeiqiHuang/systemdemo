<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE HTML>
<head>
<title>用户详细信息</title>
<%@include file="/WEB-INF/views/admin/css_Script.jsp" %>
<script type="text/javascript">

$(document).ready(function (){
    $("input[name='userStatus']").each(function (){ if(this.value == "${user.userStatus}"){ $(this).attr("checked","checked");}else{ $(this).removeAttr("checked");}});
    $("input[name='SEX']").each(function (){ if('F' == "${user.SEX}"){ $(this).val("女");}else if('M' == "${user.SEX}"){$(this).val("男");}else {$(this).val("");}});
	//$("#sf").validate({  rules: {"userName": {required:true,maxlength: 50,minlength:3},"mobilePhone": {required:true,maxlength: 120,minlength:2}}});
});
</script>
</head>
<body>
<h3 class="editContainerTitle">${not empty user.userId ? '用户详细信息':'无效用户'}</h3>
<div class="editContainer"  style="width: 100%">
    <form method="post" id="sf">
        <div class="editRow">
            	<label class="edit_lable"><em>*</em>用户ID:</label>
            	<input type="text" name="userId"  value="${user.userId}" placeholder="用户ID(唯一)" class="eidt_input" disabled="disabled" readonly="readonly">
            	<p class="edit_input_tip"></p>
            	<label class="edit_lable"><em>*</em>用户名称:</label>
            	<input type="text" name="userName"  value="${user.userName}" placeholder="用户名称(唯一)" class="eidt_input" disabled="disabled" readonly="readonly">
            	<p class="edit_input_tip"></p>
        </div>

        <div class="editRow">
            	<label class="edit_lable"><em>*</em>用户手机号:</label>
            	<input type="text" name="mobilePhone"  value="${user.mobilePhone}"  placeholder="用户手机号" id="" class="eidt_input" disabled="disabled" readonly="readonly">
            <label class="edit_lable" >状态:</label>
            <div class="edit_choicebox">
                <div class="edit_choicebox_block"><label><input type="radio" name="userStatus" value="1" disabled="disabled" readonly="readonly">正常</label><label><input type="radio" name="userStatus" value="0" disabled="disabled" readonly="readonly">禁用</label></div>
            </div>            	
            	<p class="edit_input_tip"></p>

        </div>

        <div class="editRow">
            	<label class="edit_lable"><em>*</em>用户头像URL:</label>
            	<input type="text" name="headPhotourl"  value="${user.headPhotourl}"  class="eidt_input" disabled="disabled" readonly="readonly">
            	<p class="edit_input_tip"></p>

            	<label class="edit_lable"><em>*</em>用户昵称:</label>
            	<input type="text" name="nickName"  value="${user.nickName}"  class="eidt_input" disabled="disabled" readonly="readonly">
            	<p class="edit_input_tip"></p>

        </div>
        <div class="editRow">
            	<label class="edit_lable"><em>*</em>行政区划:</label>
            	<input type="text" name="districtCode"  value="${user.districtCode}"  class="eidt_input" disabled="disabled" readonly="readonly">

            	<label class="edit_lable"><em>*</em>邮箱:</label>
            	<input type="text" name="eMail"  value="${user.eMail}"  class="eidt_input" disabled="disabled" readonly="readonly">
            	<p class="edit_input_tip"></p>

        </div>    
        <div class="editRow">
            	<label class="edit_lable"><em>*</em>性别:</label>
            	<input type="text" name="SEX"  id="SEX"  class="eidt_input" disabled="disabled" readonly="readonly">

            	<label class="edit_lable"><em>*</em>创建时间:</label>
            	<input type="text" name="createTime" value="${user.createTime}"  class="eidt_input" disabled="disabled" readonly="readonly">
            	<p class="edit_input_tip"></p>
        </div> 
        <div class="editRow">
            	<label class="edit_lable"><em>*</em>更新时间:</label>
            	<input type="text" name="updateTime"  value="${user.updateTime}" class="eidt_input" disabled="disabled" readonly="readonly">
            	<p class="edit_input_tip"></p>
        </div>

        <div class="editRow">
           <div style="text-align: center;">
           		<c:if test="${ empty user.userId }"><input type="button"  value="关闭"   class="grayBtn" onclick="javascript:parent.colseTabsByTitle('用户信息')"/></c:if>
           		<c:if test="${ not  empty user.userId }"><input type="button"  value="关闭"   class="grayBtn" onclick="javascript:parent.colseTabsByTitle('用户信息')"/></c:if>
        	</div>
        </div>
    </form>
</div>
</body>
</html>