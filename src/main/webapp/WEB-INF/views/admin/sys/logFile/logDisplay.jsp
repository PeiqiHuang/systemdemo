 <%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>日志文件</title>
<%@include file="/WEB-INF/views/admin/css_Script.jsp" %>
<style type="text/css">
.tree-title a{color: white;}
</style>
<script type="text/javascript">
function fixWidth() {
    return $("#container").width() -150 - 85 - 23;// 23：不加大chrome会显示滚动条
}
function showDownloadAdd(value,rowData,rowIndex){
  if(!value){
    return "<a href='${base}/admin/sys/logFile/downloadLogFile.do?filePath="+rowData.downloadPath+"&fileName="+rowData.fileName+"' >"+rowData.fileName+"</a>";
  }
 return rowData.fileName;
}
function showFileSize(value,rowData,rowIndex){
	if (!rowData.folder) {
	    value = format(value/1024);
	    if (value < 1024) {
		    return value + "KB";
	    } else {
	    	value = format(value/1024);
	    	if (value < 1024) {
		    return value + "MB";
		    } else {
		    	value = format(value/1024);
		    	return value + "GB";
		    }
	    }
	}
}
function format(num) {
	var value = num.toFixed(2);
	if (/00$/.test(value)) {
		return Math.floor(num);
	} else if (/0$/.test(value)) {
		return num.toFixed(1);
	} else {
		return value;
	}
}
$(function(){
$('#logFileView').treegrid({
    url: "${base}/admin/sys/logFile/fileData.do",
	onLoadError:function(XMLHttpRequest, textStatus, errorThrown){
	  $.messager.alert("消息",textStatus, "error");
	   window.document.write(XMLHttpRequest.responseText);
	},
	onLoadSuccess:function(){
		//插件bug空文件夹显示文件图标，这里把空文件夹的图标换成文件夹
		$("span").each(function(){
			if ($(this).attr("class") == "tree-icon tree-file tree-folder") {
				$(this).attr("class", "tree-icon tree-folder");
			}
		});
		//修改背景颜色
		$("div .panel-body").css("background-color", "rgb(110,110,110)");
	}
	});
});
function searchFile(){
	var filePath = $("#filePathStr").val();
	/* if(filePath == ''){
	  $.messager.alert("消息","请输入文件名", "error");
	  return;
	} */
	$.messager.progress({title:'正在处理数据,请等待....',text:''});
	$.post('${base}/admin/sys/logFile/fileData.do',{filePathStr:filePath},function(data){
		if (data[0].children.length) {
			$('#logFileView').treegrid('loadData',data);
		} else {
		  $.messager.alert("消息","搜索路径不存在", "error");
		}
		$.messager.progress('close');
	},'json');
}
</script>
</head>
<body>
<div class="ContentContainer" id="container">
<!-- <table class="SearchTable" cellspacing="1" cellpadding="4">
<tr class="Search_BtnRow" >
	 <td class="Search_Field"><input type="text" id="filePathStr" name="filePathStr" class="NoneBorderTextBox" placeholder="/opt/tomcat/tomcat_bkdyAdmin_10001/logs/"/></td>
	<td>
	<a class="easyui-linkbutton" iconCls="icon-search" href="javascript:searchFile()" >搜索</a>
	</td>
</tr>
</table>
<hr title="点南隐藏搜索框"  class="searchTabSplit"> -->
<table id="logFileView"  data-options="animate: true,idField:'id',treeField: 'folder'">
	<thead>
		<tr>
				<!-- <th data-options="field:'id'">ID</th> -->
				<th id="folder" data-options="field:'folder',formatter:showDownloadAdd,width:fixWidth()">文件名</th>
				<th data-options="field:'dateStr',width:150">创建时间</th>
				<th data-options="field:'fileSize',formatter:showFileSize,width:85">文件大小</th>
		</tr>
	</thead>
</table>
</div>
</body>
</html>