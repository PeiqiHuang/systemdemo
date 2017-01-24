<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE HTML>
<head>
<title>菜单项修改管理</title>
<%@include file="/WEB-INF/views/admin/css_Script.jsp" %>
<script type="text/javascript">
function mergeMenu(){
	if(!$("#sf").valid()){
	  	$.messager.alert("消息", "参数错误！", "error");
 		return false;
	}
	$.messager.confirm("操作提示", "${not empty menuItem.itemId ? '你确定要更新菜单项信息？':'你确定要新增菜单项信息？'}", function (flag) {
	    if (flag) {
	        $.myAjax.excuteAjaxMask({
				url:"${base}/admin/auth/menuItem/mergeMenuItem.do",
				reqData:$("#sf").serialize(),
				dataType: "json",
				successfn: function(data){
				  $.messager.alert("消息", "${not empty menuItem.itemId ? '修改成功!':'新增成功!'}！", "info",function (){
				  	window.location.href="${base}/admin/auth/menuItem/menuItemDisplay.do?itemId="+data;
				  });
				}
			});
	    }
	});
}
$(document).ready(function (){
    $("#status").val('${menuItem.status}');
	$("#sf").validate({rules: {"itemId": {required:true},"weight":{number:true,maxlength: 2,minlength:1},"itemName":{required:true,maxlength: 25,minlength:1},"key":{required:true,maxlength: 25,minlength:1}}});
	$("#toolbar a").click(function (){ $("#iconBar").val($(this).find("span").html());$("#iconBar").tooltip('hide'); });
	$("#iconBar").tooltip({hideEvent: "none",showEvent:"click",content: function(){return $('#toolbar');}});
});
</script>
</head>
<body>
<h3 class="editContainerTitle">${not empty menuItem.itemId ? '菜单项修改':'编辑菜单项'}</h3>
<div class="editContainer"  style="width: 80%">
    <form  method="post" id="sf">
    	<c:if test="${not empty  menuItem.itemId }"><input type="hidden" name="itemId" value="${ menuItem.itemId }"/></c:if>
        <div class="editRow">
            	<label class="edit_lable"><em>*</em>菜单名称:</label>
            	<input type="text" name="itemName"  value="${menuItem.itemName}" placeholder="菜单名称" class="eidt_input">
            	<p class="edit_input_tip"></p>
        </div>
        <%-- <div class="editRow" >
            <label class="edit_lable" ><em>*</em>展现位置:</label>
            	<input type="text" name="key"  value="${menuItem.key}"  placeholder="展现位置" class="eidt_input">
            	<p class="edit_input_tip"></p>
        </div> --%>
        <div class="editRow">
            	<label class="edit_lable"><em>*</em>状态:</label>
            	   <select class="DropDownList" name="status" id="status">				
	       				<option value="9">锁定</option>
	       				<option value="5">默认</option>
	       				<option value="0">普通</option>
	       				<option value="-1">屏蔽</option>
       		      </select>
        </div>
        <div class="editRow">
            	<label class="edit_lable">排序:</label>
            	<input type="text" name="weight"  value="${menuItem.weight}"  placeholder="排序" class="eidt_input">
            	<p class="edit_input_tip"></p>
        </div>
        <div class="editRow">
           <div style="text-align: center;">
           		<input type="button"  value="提交"   class="orangeBtn" onclick="mergeMenu()"/>
           		<input type="button"  value="关闭"   class="grayBtn" onclick="javascript:parent.colseTabsByTitle('菜单项编辑')"/>
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