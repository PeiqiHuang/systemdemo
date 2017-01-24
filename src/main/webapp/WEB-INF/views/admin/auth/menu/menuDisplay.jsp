<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE HTML>
<head>
<title>系统权限菜单</title>
<%@include file="/WEB-INF/views/admin/css_Script.jsp" %>
<script type="text/javascript">
function mergeMenu(){
	if(!$("#sf").valid()){
	  	$.messager.alert("消息", "参数错误！", "error");
 		return false;
	}
	$.messager.confirm("操作提示", "${not empty menu.menuId ? '你确定要更新菜单信息？':'你确定要新增菜单信息？'}", function (flag) {
	    if (flag) {
	        $.myAjax.excuteAjaxMask({
				url:"${base}/admin/auth/menu/mergeMenu.do",
				reqData:$("#sf").serialize(),
				dataType: "json",
				successfn: function(data){
				  $.messager.alert("消息", "${not empty menu.menuId ? '修改成功!':'新增成功!'}！", "info",function (){
				  	window.location.href="${base}/admin/auth/menu/menuDisplay.do?menuId="+data;
				  });
				}
			});
	    }
	});
}
$(document).ready(function (){
    $("#menuType").val('${menu.menuType}');
	$("#sf").validate({  rules: {"parentId": {required:true,number: true},"sortNum":{required:true,number:true},"menuName":{required:true,maxlength: 12,minlength:3},"linkAddress":{maxlength: 100,minlength:3}}});
	$("#toolbar a").click(function (){ $("#iconBar").val($(this).find("span").html());$("#iconBar").tooltip('hide'); });
	$("#iconBar").tooltip({hideEvent: "none",showEvent:"click",content: function(){return $('#toolbar');}});
});
</script>
</head>
<body>
<h3 class="editContainerTitle">${not empty menu.menuId ? '系统权限菜单修改':'添加权限菜单'}</h3>
<div class="editContainer"  style="width: 80%">
    <form  method="post" id="sf">
    	<c:if test="${not empty  menu.menuId }"><input type="hidden" name="menuId" value="${ menu.menuId }"/></c:if>
        <div class="editRow">
            	<label class="edit_lable"><em>*</em>菜单名称:</label>
            	<input type="text" name="menuName"  value="${menu.menuName}" placeholder="菜单名称" class="eidt_input">
            	<p class="edit_input_tip"></p>
        </div>
        <div class="editRow">
            	<label class="edit_lable">链接地址:</label>
            	<input type="text" name="linkAddress"  value="${menu.linkAddress}"  placeholder="链接地址" class="eidt_input">
            	<p class="edit_input_tip"></p>
        </div>
   		<div class="editRow">
            	<label class="edit_lable"><em>*</em>菜单类型:</label>
            	   <select class="DropDownList" name="menuType" id="menuType">
	       				<option value="1">系统级</option>
	       				<option value="2">菜单级</option>
	       				<option value="3">按纽级</option>
       		      </select>
        </div>
        <div class="editRow" >
            <label class="edit_lable" >菜单ICON:</label>
            	<input type="text" name="menuIcon"  value="${menu.menuIcon}"  placeholder="菜单ICON"  class="eidt_input"  id="iconBar">
            	<p class="edit_input_tip"></p>
        </div>
          <div class="editRow">
            	<label class="edit_lable"><em>*</em>排序:</label>
            	<input type="text" name="sortNum"  value="${ not empty  menu.sortNum ? menu.sortNum:0}"  placeholder="排序序号" class="eidt_input">
            	<p class="edit_input_tip"></p>
        </div>
         <div class="editRow">
            	<label class="edit_lable"><em>*</em>父ID:</label>
            	<input type="text" name="parentId"  value="${ not empty  menu.parentId ? menu.parentId:0}"  placeholder="父ID" class="eidt_input">
            	<p class="edit_input_tip"></p>
        </div>
        <div class="editRow">
           <div style="text-align: center;">
           		<input type="button"  value="提交"   class="orangeBtn" onclick="mergeMenu()"/>
           		<input type="button"  value="关闭"   class="grayBtn" onclick="javascript:parent.colseTabsByTitle('菜单编辑')"/>
        	</div>
        </div>
    </form>
</div>
<div style="display:none">
  <div id="toolbar">
      <a href="#"><img src="${base}/resource/admin/images/nav.png"><span>icon-nav</span></a><br>
      <a href="#"><img src="${base}/resource/admin/images/nav_card.png"><span>icon-nav-card</span></a><br>
      <a href="#"><img src="${base}/resource/admin/images/nav_chart.png"><span>icon-nav-chart</span></a><br>
      <a href="#"><img src="${base}/resource/admin/images/nav_edit.png"><span>icon-nav-edit</span></a><br>
      <a href="#"><img src="${base}/resource/admin/images/nav_express.png"><span>icon-nav-express</span></a><br>
      <a href="#"><img src="${base}/resource/admin/images/nav_list.png"><span>icon-nav-list</span></a><br>
      <a href="#"><img src="${base}/resource/admin/images/nav_man.png"><span>icon-nav-man</span></a><br>
      <a href="#"><img src="${base}/resource/admin/images/nav_sys.png"><span>icon-nav-sys</span></a><br>
      <a href="#"><img src="${base}/resource/admin/images/nav_cfg.png"><span>icon-nav-cfg</span></a><br>
      <a href="#"><img src="${base}/resource/admin/images/nav_permission.png"><span>icon-nav-permission</span></a><br>
      <a href="#"><img src="${base}/resource/admin/images/nav_data.png"><span>icon-nav-data</span></a><br>
      <a href="#"><img src="${base}/resource/admin/images/nav_user.png"><span>icon-nav-user</span></a>
  </div>
</div>
</body>
</html>