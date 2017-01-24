<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE HTML>
<head>
<title>中国邮政官方APP管理后台</title>
<link rel="shortcut icon" href="${base}/resource/admin/images/wx-logo.jpg" type="image/x-icon">
<meta name="robots" content="none" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" type="text/css" href="${base}/resource/admin/css/ui.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resource/admin/css/customer.css" />
<link rel="stylesheet" type="text/css" href="${base}/resource/admin/css/icon.css" />
<script type="text/javascript" src="${base}/resource/admin/js/jquery-1.7.2.min.js" ></script>
<script type="text/javascript" src="${base}/resource/admin/js/jquery.easyui.min.js" ></script>
<script type="text/javascript" src="${base}/resource/admin/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${base}/resource/admin/js/myAjax.js" ></script>
<script type="text/javascript" src="${base}/resource/admin/js/myCommon.js"></script>
<script type="text/javascript" src="${base}/resource/admin/js/menu.js" ></script>
<script type="text/javascript">
$(function() {
	// 注册系统级菜单打开里ajax加载二级菜单
	 $( ".easyui-accordion" ).accordion({
      onSelect: function (){
          var pp = $('.easyui-accordion').accordion('getSelected'); 
          if($.trim(pp.html()) ==""){
           		addMenu(pp,"${base}/admin/sys/userSubMenuList.do","${base}");
          }
      }
    });
    // 默认显示第一个
    addMenu($('.easyui-accordion').accordion('getSelected'),"${base}/admin/sys/userSubMenuList.do","${base}"); 
    //注册选项板事件
    tabCloseEven();
   $("#modifyPwdForm").validate({rules: {"oldPwd": {required:true},"newPwd": {required:true,minlength:6,maxlength:15},"repeatPwd": {required:true,minlength:6,maxlength:15,equalTo:"#newPwd"}}});
});
function modifyPwdWin(){
	if(!$("#modifyPwdForm").valid()){
	  	$.messager.alert("消息", "参数错误！", "error");
 		return false;
	}
	$.messager.confirm("操作提示", "您确定要修改登录密码吗?", function (flag) {
	    if (flag) {
	         $.myAjax.excuteAjaxMask({
				url:"${base}/admin/sys/modifyLoginPwd.do",
				reqData:$("#modifyPwdForm").serialize(),
				dataType: "json",
				successfn: function(data){
				     if(data.status == 0 ){
				        $.messager.alert("消息", "修改密码成功", "info");
				        $('#win').window('close', true); 
				     }else{
				        $.messager.alert("消息", "旧登录密码不正确！", "error");
				     }
				}
			});
	    }
	});
}
</script>
</head>
<body class="easyui-layout" style="overflow-y:hidden" scroll="no">
<%--头部--%>
<div region="north"  border="false" class="north" style="overflow: hidden; height:65px;background-color:#009900" >
	<div class="north-logo"><img src="${base}/resource/admin/images/logo.png"/></div>
	<div class="north-fun">
<!-- 		<a href="" class="msg"></a> -->
		<div class="userinfo" onclick="openSimpleEWin('win',400,300,'修改登录密码')" title="点击修改登密码">
			<p class="u-name">${loginUser.loginName}</p>
			<p class="u-role">${loginUser.realName}</p>
		</div>
		<a href="${base}/admin/sys/logout.do" class="loginout"></a>
	</div>
</div>
<%--左侧菜单栏--%>
<div region="west" split="false" title="权限菜单" style="width:250px;" id="west" class="cs-west">
	<div class="easyui-accordion" fit="false" border="false">
	     <c:forEach items="${menuLists}" var="item">
		     <div title="${item.menuName}"  iconCls="${item.menuIcon}" menuId="${item.menuId}">
		     </div>
	     </c:forEach>
	</div>
</div>
<%--main显示板 --%>
<div id="mainPanle" region="center" style="overflow-y:hidden">
	<div id="tabs" class="easyui-tabs" fit="true" border="false">
		<div title="欢迎使用"  style="padding:10px;overflow:hidden;background-color: #F5F5F5;" id="home"></div>
	</div>
</div>
<%--菜单栏右键 --%>
<div id="mm" class="easyui-menu" style="width:150px;">
	<div id="mm-tabclose">关闭</div>
	<div id="mm-tabcloseall">全部关闭</div>
	<div id="mm-tabcloseother">除此之外全部关闭</div>
	<div class="menu-sep"></div>
	<div id="mm-tabcloseright">当前页右侧全部关闭</div>
	<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
	<div class="menu-sep"></div>
	<div id="mm-exit">退出</div>
</div>
<%--弹窗 --%>
<div id="win" colsed="true">
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding: 8px; background: #fff; border: 1px solid #ccc;">
        <form id="modifyPwdForm">
    	<table cellspacing="1" cellpadding="0" class="ContentTable">
        <tr>
            <td class="Label"><span style="color: black;font-size: 15px">原密码:</span></td>
            <td class="Field"><input type="password" name="oldPwd" class="NoneBorderTextBox"/></td>
       </tr>
       <tr>
            <td class="Label"><span style="color: black;font-size: 15px">新密码:</span></td>    
            <td class="Field"><input type="password" name="newPwd" class="NoneBorderTextBox" id="newPwd"/></td>
       </tr>
        <tr>
        	<td class="Label"><span style="color: black;font-size: 15px">重复新密码:</span></td>    
            <td class="Field"><input type="password" name="repeatPwd" class="NoneBorderTextBox"/></td>
        </tr>
      </table>
      </form>
    </div>
    <div region="south" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;text-align: right;">
      <a class="easyui-linkbutton" iconCls="icon-save" href="javascript:modifyPwdWin()" >保存修改</a>
    </div>
 </div>
</div>
</body>
</html>