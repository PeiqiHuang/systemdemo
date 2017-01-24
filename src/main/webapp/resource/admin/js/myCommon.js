$(function() {
	$(".ListTable .ListTableContent tr ").click(function() {
		$(".ListTableSelectedRow").removeClass("ListTableSelectedRow");
		$(this).addClass("ListTableSelectedRow");
	});
});
function gotoPage(sobj) {
	if($(sobj).attr("topage") != $(sobj).attr("nowpage")){
		$("#" + $(sobj).attr("pf") + " #toPage").val($(sobj).attr("topage"));
		$("#" + $(sobj).attr("pf")).submit();
	}
}
function changePageSize(sobj){
	$("#" + $(sobj).attr("pf") + " #toPage").val(1);
	$("#" + $(sobj).attr("pf") + " #pageSize").val($(sobj).val());
	$("#" + $(sobj).attr("pf")).submit();
}
function openSimpleEWin(windId,width,height,title){
$('#'+windId).window({width:width,height:height,title:title,collapsible:false,minimizable:false,maximizable:false,draggable:true,resizable:false,modal:true});
}
function pcaDoubleTag(url,opObj,nextSelId){
$.myAjax.excuteAjaxMask({
	url:url,
	reqData:"districtCode="+$(opObj).val(),
	dataType: "json",
	successfn: function(data){
	    var soText="<option value='"+$(opObj).val()+"'>全部</option>";
	  	$.each(data,function (i,d){soText+="<option value ='"+d.districtCode+"'>"+d.districtName+"</option>";});
     	$("#"+nextSelId).html(soText);
	}
});
}