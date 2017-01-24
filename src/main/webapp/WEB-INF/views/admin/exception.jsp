<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>exception</title>
<link rel="stylesheet" type="text/css" href="${base}/resource/admin/css/customer.css" />
<script src="${base}/resource/admin/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script>
function showemsg(){
$("#emsgDiv").show();
}
</script>
</head>
<body style="text-align:center;margin-top:50px;margin-left: 50px;">
	<div style="font-size:14px;border:#7B9EBD 1px solid;background:#EFF7FF;width:80%;padding:10px;text-align:left;">
		<img src="${base}/resource/admin/images/msg_error.gif" border="0" align="absmiddle" onclick="showemsg()"/>&nbsp; 对不起，您所访问的页面暂时不能访问……
	</div>
	<div style="font-size:14px;border:#7B9EBD 1px solid;background:#FFC0CB;width:80%;padding:10px;text-align:left;display: none" id="emsgDiv">
	    ${exception}
	</div>
</body>
</html>
