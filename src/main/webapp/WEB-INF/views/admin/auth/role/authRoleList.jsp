<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="myTag" uri="/WEB-INF/myTag.tld" %>
<!DOCTYPE HTML>
<html>
<head>
<title>系统角色列表</title>
<%@include file="/WEB-INF/views/admin/css_Script.jsp" %>
<script type="text/javascript">
function searchForm(){
$("#toPage").val(1);
$("#sf").submit();
return false;
}
function modifyRole(){
	if(!$("#roleWinForm").valid()){
	  	$.messager.alert("消息", "参数错误！", "error");
 		return false;
	}
	$.messager.confirm("操作提示", "您确定要保存该信息吗?", function (flag) {
	    if (flag) {
	         $.myAjax.excuteAjaxMask({
				url:"${base}/admin/auth/mergeSysRole.do",
				reqData:$("#roleWinForm").serialize(),
				dataType: "json",
				successfn: function(data){
				    if(data && data.status =='0'){
				         $.messager.alert("消息", "保存成功!", "info",function (){
				              var win = $.messager.progress({title:'请等待...',text:''});
				              window.location.href="${base}/admin/auth/authRoleList.do";
				         });
				    }else{
				     	$.messager.alert("消息", "保存失败!", "error");
				    }
				}
			});
	    }
	});
}
function deleteRole(roleId){
	$.messager.confirm("操作提示", "您确定要删除么?", function (flag) {
	    if (flag) {
	         $.myAjax.excuteAjaxMask({
				url:"${base}/admin/auth/delSysRole.do",
				reqData:"roleId="+roleId,
				dataType: "text",
				successfn: function(data){
				     if(data == 0 ){
				        $.messager.alert("消息", "删除成功!", "info",function (){
				        	 var win = $.messager.progress({title:'请等待...',text:''});
				              window.location.href="${base}/admin/auth/authRoleList.do";
				         });
				     }else{
				        $.messager.alert("消息", "删除失败!", "error");
				     }
				}
			});
	    }
	});
}
function roleDetail(roleId){
	$.myAjax.excuteAjaxMask({
			url:"${base}/admin/auth/roleDetail.do",
			reqData:"roleId="+roleId,
			dataType: "json",
			successfn: function(data){
			    $("#roleWinForm input[name='roleName']").val(data.roleName);
			    $("#roleWinForm input[name='remark']").val(data.remark);
			    $("#roleWinForm input[name='roleId']").val(data.roleId);
			   $("#roleWin").window("open"); 
			}
	});
}
$(document).ready(function (){
 	$("#roleWin").window({width:500,height:250,title:"角色编辑",closed: true,collapsible:false,minimizable:false,maximizable:false,draggable:true,resizable:false,modal:true,onBeforeClose: function () { $("#roleWinForm")[0].reset(); $("#roleWin").window("close", true); }});
	$("#sf").validate({ submitHandler:function(form){var win = $.messager.progress({title:'请等待...',text:''});form.submit()} });
	$("#roleWinForm").validate({rules: {"roleName": {required:true,maxlength: 12,minlength:3},"remark": {required:true,maxlength: 60,minlength:3}},errorPlacement: function (error, element) {$(element).css("background-color","pink");},success: function (label, element) {$(element).css("background-color","");}});
	$("#newBtn").click(function(){
		$('#roleWin').window('open');
	})
	$("#saveBtn").click(function(){
		modifyRole();
	})
});
</script>
</head>
<body>
<div class="ContentContainer">
<form action="${base}/admin/auth/authRoleList.do" method="post" id="sf">
	<table class="SearchTable" cellspacing="1" cellpadding="4">
		<tr>
			 <td class="Search_Label">名称:</td>
			 <td class="Search_Field"><input type="text" name="roleName" value="${authRole.roleName}" class="NoneBorderTextBox" /></td>
			 <td class="Search_Label">备注:</td>
			 <td class="Search_Field"><input type="text" name="remark" value="${authRole.remark}" class="NoneBorderTextBox" /></td>
		</tr>
		<tr>
			<td colspan="4" class="Search_BtnRow" >
				<a class="easyui-linkbutton" iconCls="icon-add" style="width: 80px" id="newBtn">新增</a><!--  href="javascript:$('#roleWin').window('open')"  -->
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
			<th>ID</th>
			<th>角色名称</th>
			<th>备注</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		<tbody class="ListTableContent">
		    <c:forEach items="${pagin.resultLists}" var="item" varStatus="vs">
			<tr class="${vs.count%2 ==0 ? 'ListTableOddRow':'ListTableEvenRow'}">
				<td>${item.roleId }</td>
				<td>${item.roleName}</td>
				<td>${item.remark}</td>
				<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td class="actionBar">
				<a href="javascript:roleDetail('${item.roleId}')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">编辑</a>
				<a href="javascript:parent.addTab('角色权限视图','${base}/admin/auth/roleRightView.do?roleId=${item.roleId}')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-config'" >权限</a>
				<a href="javascript:deleteRole('${item.roleId}')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'" >删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
		<myTag:paginTag colspan="5" pagination="${pagin}" pageFormId="sf"/>
	</table>
</div>
</body>
<%--弹窗 --%>
<div id="roleWin" colsed="true">
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding: 8px; background: #fff; border: 1px solid #ccc;">
        <form id="roleWinForm"><input type="hidden" name="roleId" />
    	<table cellspacing="1" cellpadding="0" class="ContentTable">
        <tr>
            <td class="Label"><span style="color: black;font-size: 15px">名称:</span></td>
            <td class="Field"><input type="text" name="roleName" class="NoneBorderTextBox"/></td>
       </tr>
       <tr>
            <td class="Label"><span style="color: black;font-size: 15px">备注:</span></td>    
            <td class="Field"><input type="text" name="remark" class="NoneBorderTextBox" /></td>
       </tr>
      </table>
      </form>
    </div>
    <div region="south" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;text-align: right;">
      <a class="easyui-linkbutton" iconCls="icon-save" id="saveBtn">保存</a><!-- href="javascript:modifyRole()" -->
    </div>
 </div>
</div>
</html>