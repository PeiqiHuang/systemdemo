 <%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>角色权限编辑</title>
<%@include file="/WEB-INF/views/admin/css_Script.jsp" %>
<style type="text/css">
.tree-title {font-size: 15px}
</style>
<script type="text/javascript">
// checkBox
function checkBoxshow(value,rowData,rowIndex){
	var checkboxHtml ="<input type='checkbox' name='menuIds' value='"+rowData.menuId+"' id='"+rowData.menuId+"' "+"' parentId='"+rowData.parentId+"' ";
	if(rowData.checkFlag !='' && rowData.checkFlag.length >10){
	   checkboxHtml=checkboxHtml+"checked='checked'";
	}
	return checkboxHtml+"/>"+rowData.menuName;
}
// 区分菜单的等级颜色
function showMenuType(value,rowData,rowIndex){
	if(value =="1"){
	  	 return "<p style='font-size:15px;color:#FF8888'>系统级</p>";
	}else if(value=="2"){
	 	 return "<p style='font-size:13px;color:#BBBB00'>菜单级</p>";
	}else{
	  	return "<p style='font-size:11px;color:#CC6600'>按纽级</p>";
	}
}
$(function(){
$("#roleRightView").treegrid({
    url: "${base}/admin/auth/roleRightData.do?roleId=${authRole.roleId}",
	onLoadError:function(XMLHttpRequest, textStatus, errorThrown){
	  $.messager.alert("消息",textStatus, "error");
	   window.document.write(XMLHttpRequest.responseText);
	},
	onLoadSuccess: function () {  $("#roleRightView").treegrid('collapseAll')},
	onClickRow:function (rowData){
	  var checkBox = $("#"+rowData.menuId);
	    //选中节点
	    if(checkBox.attr("checked")){
	    	//父节点选中
		    $("#"+rowData.parentId).attr("checked","checked");
		    //父节点的父节点选中
	        $("#"+$("#"+rowData.parentId).attr("parentId")).attr("checked","checked");
	    }
	    //处理子节点
	    $.each(rowData.children,function (i,val){
	     	$("#"+val.menuId).attr("checked",checkBox.attr("checked")?true:false);
	     	// 下面是否还有子节点
	     	$.each(val.children,function (k,lastNode){
	     		$("#"+lastNode.menuId).attr("checked",checkBox.attr("checked")?true:false);
	     	});
	    });
     }
	});
});
function saveRoleRightData(){
	$.messager.confirm("操作提示", "你确定要更新角色权限内容吗？", function (flag) {
	    if (flag) {
	        $.myAjax.excuteAjaxMask({
				url:"${base}/admin/auth/updateRoleRight.do",
				reqData:$("#sf").serialize(),
				dataType: "json",
				successfn: function(data){
				    if(data==0){
				         $.messager.alert("消息", "保存成功！", "info");
				     }else if(data.status  ==-2){
				        $.messager.alert("消息", "保存失败！", "error");     
				    }
				}
			});
	    }
	});
}
</script>
</head>
<body>
<div class="ContentContainer">
<table class="SearchTable" cellspacing="1" cellpadding="4">
<tr><td class="Search_BtnRow" ><a class="easyui-linkbutton" iconCls="icon-redo" href="javascript:$('#roleRightView').treegrid('collapseAll')"  style="width: 80px">折叠</a><a class="easyui-linkbutton" iconCls="icon-save" href="javascript:saveRoleRightData()"  style="width: 80px">保存</a></td></tr>
</table>
<hr title="点南隐藏搜索框"  class="searchTabSplit">
<form id="sf"><input type="hidden" name="roleId" value="${authRole.roleId }"/>
<table id="roleRightView" title="角色:[ ${authRole.roleName } ]权限视图"  data-options="animate: true,idField:'menuId',treeField: 'menuName'">
	<thead>
		<tr>
				<th data-options="field:'menuId'">菜单ID</th>
				<th data-options="field:'menuName',formatter:checkBoxshow">菜单名称</th>
				<th data-options="field:'menuType',formatter:showMenuType">菜单类型</th>
				<th data-options="field:'linkAddress'">链接地址</th>
				<th data-options="field:'parentId'">父菜单ID</th>
		</tr>
	</thead>
</table>
</form>
</div>
</body>
</html>