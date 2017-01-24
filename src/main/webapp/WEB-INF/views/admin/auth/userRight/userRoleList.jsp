<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE HTML>
<html>
<head>
<title>系统用户角色列表</title>
<%@include file="/WEB-INF/views/admin/css_Script.jsp" %>
<script type="text/javascript">
function searchForm(){
$("#toPage").val(1);
$("#sf").submit();
return false;
}
function addUserRole(){
	if($("#roleId").val() == '' ){
	  	$.messager.alert("消息", "请填写角色编码！", "error");
 		return false;
	}
	$.messager.confirm("操作提示", "您确定要保存该信息吗?", function (flag) {
	    if (flag) {
	         $.myAjax.excuteAjaxMask({
				url:"${base}/admin/auth/addRole2User.do",
				reqData:"suId=${sysUser.suId}&roleId="+$("#roleId").val() ,
				dataType: "json",
				successfn: function(data){
				    if(data  == 0){
				         $.messager.alert("消息", "保存成功!", "info",function (){
				              var win = $.messager.progress({title:'请等待...',text:''});
				              window.location.href="${base}/admin/auth/userRoleList.do?suId=${sysUser.suId}";
				         });
				    }else{
				     	$.messager.alert("消息", "保存失败!", "error");
				    }
				}
			});
	    }
	});
}
function deleteUserRole(urId){
	$.messager.confirm("操作提示", "您确定要删除么?", function (flag) {
	    if (flag) {
	         $.myAjax.excuteAjaxMask({
				url:"${base}/admin/auth/delUserRole.do",
				reqData:"suId=${sysUser.suId}&urId="+urId,
				dataType: "text",
				successfn: function(data){
				     if(data == 0 ){
				        $.messager.alert("消息", "删除成功!", "info",function (){
				        	 var win = $.messager.progress({title:'请等待...',text:''});
				               window.location.href="${base}/admin/auth/userRoleList.do?suId=${sysUser.suId}";
				         });
				     }else{
				        $.messager.alert("消息", "删除失败!", "error");
				     }
				}
			});
	    }
	});
}
$(document).ready(function (){});
</script>
</head>
<body>
<div class="ContentContainer">
<table class="SearchTable" cellspacing="1" cellpadding="4">
	<tr>
		<td  class="Search_BtnRow" style="text-align: left">用户ID:<span style="color: #FF8888;padding-right: 12px;">${sysUser.suId }</span>登录名:<span style="color: #FF8888;padding-right: 12px;">${sysUser.loginName }</span>真实姓名:<span style="color: #FFA488;">${sysUser.realName }</span></td>
		<td class="Search_BtnRow" >
			<a class="easyui-linkbutton" iconCls="icon-add"  href="javascript:openSimpleEWin('addUserRoleWin',400,200,'添加角色')" style="width: 80px">新增</a>
		</td>
	</tr>
</table>
	<hr title="点南隐藏搜索框" class="searchTabSplit">
	<table class="ListTable" cellspacing="1" cellpadding="4">
		<tr class="ListTableHeader">
			<th>用户角色关联ID</th>
			<th>角色编码</th>
			<th>角色名称</th>
			<th>备注</th>
			<th>操作</th>
		</tr>
		<tbody class="ListTableContent">
		    <c:forEach items="${roleLists}" var="item" varStatus="vs">
			<tr class="${vs.count%2 ==0 ? 'ListTableOddRow':'ListTableEvenRow'}">
				<td>${item.urId }</td>
				<td>${item.roleId}</td>
				<td>${item.roleName}</td>
				<td>${item.roleRemark}</td>
				<td class="actionBar"><a href="javascript:deleteUserRole('${item.urId}')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'" >删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</body>
<%--弹窗 --%>
<div id="addUserRoleWin" colsed="true">
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding: 8px; background: #fff; border: 1px solid #ccc;">
        <form id="menuRoleWinForm"><input type="hidden" name="mrId" />
    	<table cellspacing="1" cellpadding="0" class="ContentTable">
        <tr>
            <td class="Label"><span style="color: black;font-size: 15px">角色编码:</span></td>
            <td class="Field"><input type="text" name="roleId" class="NoneBorderTextBox" id="roleId"/></td>
       </tr>
      </table>
      </form>
    </div>
    <div region="south" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;text-align: right;">
      <a class="easyui-linkbutton" iconCls="icon-save" href="javascript:addUserRole()">添加</a>
    </div>
 </div>
</div>
</html>