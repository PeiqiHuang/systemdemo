<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="myTag" uri="/WEB-INF/myTag.tld" %>
<!DOCTYPE HTML>
<html>
<head>
<title>系统用户列表</title>
<%@include file="/WEB-INF/views/admin/css_Script.jsp" %>
<script type="text/javascript">
function searchForm(){
$("#toPage").val(1);
$("#sf").submit();
return false;
}
function resetPwd(){
	if($("input[name='resetSuId']:checked").size()==0){
	  	$.messager.alert("消息", "请选择要重置密码的用户！", "error");
	  	return;
	}
	$.messager.confirm("操作提示", "您确定要重置该系统用户的密码?", function (flag) {
	    if (flag) {
	         $.myAjax.excuteAjaxMask({
				url:"${base}/admin/sys/user/resetUserPwd.do",
				reqData:"suId="+$("input[name='resetSuId']:checked").val(),
				dataType: "text",
				successfn: function(data){console.log(data);
				    if(data =='"SUCCESS"'){
				         $.messager.alert("消息", "重置密码成功!", "info");
				    }else{
				     	$.messager.alert("消息", "重置密码失败!", "error");
				    }
				}
			});
	    }
	});
}
$(document).ready(function (){
	$("#sf").validate({ submitHandler:function(form){var win = $.messager.progress({title:'请等待...',text:''});form.submit()} });
});
</script>
</head>
<body>
<div class="ContentContainer">
<form action="${base}/admin/auth/userList.do" method="post" id="sf">
	<table class="SearchTable" cellspacing="1" cellpadding="4">
		<tr>
			 <td class="Search_Label">登录名:</td>
			 <td class="Search_Field"><input type="text" name="loginName" value="${sysUser.loginName}" class="NoneBorderTextBox" /></td>
			 <td class="Search_Label">真实姓名:</td>
			 <td class="Search_Field"><input type="text" name="realName" value="${sysUser.realName}" class="NoneBorderTextBox" /></td>
		</tr>
		<tr>
			<td colspan="4" class="Search_BtnRow" >
				<a class="easyui-linkbutton" iconCls="icon-reload"  href="javascript:resetPwd()"  style="width: 120px">重置密码</a>
				<a class="easyui-linkbutton" iconCls="icon-add" href="javascript:parent.addTab('用户信息','${base}/admin/sys/user/userDisplay.do')" style="width: 80px">新增</a>
				<a class="easyui-linkbutton" iconCls="icon-search" href="javascript:searchForm()"  style="width: 80px">搜索</a>
			</td>
		</tr>
	</table>
		<input type="hidden" name="toPage" value="${pagin.toPage}" id="toPage" />
		<input type="hidden" name="pageSize" value="${pagin.pageSize}" id="pageSize" />
</form>
	<hr title="点南隐藏搜索框" class="searchTabSplit">
	<table class="ListTable" cellspacing="1" cellpadding="4">
		<tr class="ListTableHeader">
			<th>用户ID</th>
			<th>登录名</th>
			<th>真实姓名</th>
			<th>状态</th>
			<th>更新者信息</th>
			<th>最后更新时间</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		<tbody class="ListTableContent">
		    <c:forEach items="${pagin.resultLists}" var="item" varStatus="vs">
			<tr class="${vs.count%2 ==0 ? 'ListTableOddRow':'ListTableEvenRow'}">
				<td><input type="radio"  name="resetSuId" value="${item.suId}"/>${item.suId }</td>
				<td>${item.loginName}</td>
				<td>${item.realName}</td>
				<td>${item.status eq 0 ? "正常":"<font color='red'>禁用</font>"}</td>
				<td>${item.modifier}</td>
				<td><fmt:formatDate value="${item.modifierTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td class="actionBar">
				<c:if test="${item.suId ne 1}">
					<a href="javascript:parent.addTab('用户信息','${base}/admin/sys/user/userDisplay.do?suId=${item.suId}')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" >编辑</a>
					<a href="javascript:parent.addTab('角色编辑','${base}/admin/auth/userRoleList.do?suId=${item.suId}')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-userAdd'" >角色</a>
					<a href="javascript:parent.addTab('用户权限视图','${base}/admin/auth/userRightView.do?suId=${item.suId}')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-keychain'" >权限</a>
				</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
		<myTag:paginTag colspan="8" pagination="${pagin}" pageFormId="sf"/>
	</table>
</div>
</body>
</html>