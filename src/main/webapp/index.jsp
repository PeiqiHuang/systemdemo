<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>中国邮政官方APP管理后台</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resource/admin/images/wx-logo.jpg" type="image/x-icon">
<meta name="robots" content="none" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${pageContext.request.contextPath}/resource/admin/css/login.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/admin/css/ui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/admin/js/jquery-1.7.2.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/admin/js/jquery.easyui.min.js" ></script>
<script type="text/javascript">
function login(){
if($("#loginName").val() == '' || $("#loginName").val() == '手机号/用户名'){
    $.messager.alert("消息", "请填写登录名!", "warning"); 
    return false;
}
if($("#loginPwd").val() == ''){
    $.messager.alert("消息", "请填写密码!", "warning"); 
    return false;
}
if($("#loginVc").val() == '' || $("#loginVc").val() == '验证码' ){
    $.messager.alert("消息", "请填写图形验证码!", "warning"); 
    return false;
}
pf.submit();
}
function reloadVc(){
document.getElementById("imgNumPic").src="${pageContext.request.contextPath}/kaptcha?radomcode="+Math.random();
}
$(document).ready(function(){ 
     $(document).keydown(function(e){ 
         var curKey = e.which; 
         if(curKey == 13){ 
            login(); 
             return false; 
         } 
     });
//      $("#loginName").blur(function(){if ($(this).val() == '') {$(this).val('用户名');}}).focus(function (){$(this).val('')});
//      $("#loginVc").blur(function(){if ($(this).val() == '') {$(this).val('验证码');}}).focus(function (){$(this).val('')}); 
}); 
</script>
</head>
<body id="login" >
  <div class="login-logo">
  		<img src="${pageContext.request.contextPath}/resource/admin/images/logo.png"/>
  </div>
  <div class="loginInputArea">
  	  <div class="loginInputArea-wrap">
  	  	  <div class="img-wrap"><img src="${pageContext.request.contextPath}/resource/admin/images/bgPhone.png" /></div>
		  <form action="${pageContext.request.contextPath}/admin/sys/login.do" id="pf">
			<div class="text user"><input class="loginName" id="loginName" placeholder="用户名"  name="loginName"  type="text" class="text" value="${empty sessionScope.loginName ? '': sessionScope.loginName}" ></div>
			<div class="password"><input class="loginPwd" id="loginPwd" placeholder="密码" name="loginPwd" type="password"  value="${empty sessionScope.loginPwd ? '': sessionScope.loginPwd}"/></div>
			<div class="text"><input class="loginVc" id="loginVc"  placeholder="验证码"  name="loginVc"  type="text" class="text"><img class="imgNumPic" id="imgNumPic" name="imgNumPic" src="${pageContext.request.contextPath}/kaptcha" alt="校验码" align="absmiddle" onclick="reloadVc()"/></div>
			<div class="submit" onclick="login()"><input type="button" value="登录"></div>
<!-- 			<div class="alink"><p><a href="#">忘记密码 ?</a></p></div> -->
		  </form>
  	  </div>
  </div>
   <!-- <div class="copy_layout">
      <p>Copyright© 全国邮政电子商务运营中心<br> Version1.0</p>
   </div> -->
<script type="text/javascript">
  if('${sessionScope.msg}' !=''){
    $.messager.alert("消息", "${sessionScope.msg}", "warning"); 
}
</script>
</body>
</html>