<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>illegalPermission</title>
<script src="${pageContext.request.contextPath}/resource/admin/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script>
function showemsg(){
$("#emsgDiv").show();
}
</script>
</head>
<body style="text-align:center;margin-top:50px;margin-left: 50px;background-color: #F5F5F5;">
	<div style="font-size:20px;border:#7B9EBD 1px solid;background:#EFF7FF;width:80%;padding:10px;text-align:left;">
		<img src="${pageContext.request.contextPath}/resource/admin/images/msg_error.gif" border="0" align="absmiddle" onclick="showemsg()"/>&nbsp; 对不起，您的权限不足访问对应的资源……
	</div>
	<div style="font-size:18px;border:#7B9EBD 1px solid;background:#FFC0CB;width:80%;padding:10px;text-align:left;display: none" id="emsgDiv">${param.reqPath}</div>
</body>
</html>
