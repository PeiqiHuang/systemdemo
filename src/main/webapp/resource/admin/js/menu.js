function addTab(title, url) {
	if ($("#tabs").tabs("exists", title)) {
		$("#tabs").tabs("select", title);
		var currTab = $("#tabs").tabs("getSelected");
		var curl = $(currTab.panel("options").content).attr("src");
		if (curl != undefined && curl != url) {
			$("#tabs").tabs("update", {
				tab : currTab,
				options : {
					content : createFrame(url)
				}
			})
		}
	} else {
		var content = createFrame(url);
		$("#tabs").tabs("add", {
			title : title,
			content : content,
			closable : true
		})
	}
	tabClose()
}
function createFrame(url) {
	var s = '<iframe scrolling="auto" frameborder="0"  src="' + url
			+ '" style="width:100%;height:100%;"></iframe>';
	return s
}
function tabClose() {
	$(".tabs-inner").dblclick(function() {
		var subtitle = $(this).children(".tabs-closable").text();
		$("#tabs").tabs("close", subtitle)
	});
	$(".tabs-inner").bind("contextmenu", function(e) {
		$("#mm").menu("show", {
			left : e.pageX,
			top : e.pageY
		});
		var subtitle = $(this).children(".tabs-closable").text();
		$("#mm").data("currtab", subtitle);
		$("#tabs").tabs("select", subtitle);
		return false
	})
}
function tabCloseEven() {
	$("#mm-tabclose").click(function() {
		var currtab_title = $("#mm").data("currtab");
		$("#tabs").tabs("close", currtab_title)
	});
	$("#mm-tabcloseall").click(function() {
		$(".tabs-inner span").each(function(i, n) {
			var t = $(n).text();
			$("#tabs").tabs("close", t)
		})
	});
	$("#mm-tabcloseother").click(function() {
		var currtab_title = $("#mm").data("currtab");
		$(".tabs-inner span").each(function(i, n) {
			var t = $(n).text();
			if (t != currtab_title) {
				$("#tabs").tabs("close", t)
			}
		})
	});
	$("#mm-tabcloseright").click(function() {
		var nextall = $(".tabs-selected").nextAll();
		if (nextall.length == 0) {
			return false
		}
		nextall.each(function(i, n) {
			var t = $("a:eq(0) span", $(n)).text();
			$("#tabs").tabs("close", t)
		});
		return false
	});
	$("#mm-tabcloseleft").click(function() {
		var prevall = $(".tabs-selected").prevAll();
		if (prevall.length == 0) {
			return false
		}
		prevall.each(function(i, n) {
			var t = $("a:eq(0) span", $(n)).text();
			$("#tabs").tabs("close", t)
		});
		return false
	});
	$("#mm-exit").click(function() {
		$("#mm").menu("hide")
	})
}
function menuEvent() {
	$(".cs-navi-tab").click(function() {
		var $this = $(this);
		var href = $this.attr("src");
		var title = $this.text();
		addTab(title, href);
		$(".easyui-accordion li div").removeClass("selected");
		$(this).find("div").addClass("selected")
	}).hover(function() {
		$(this).find("div").addClass("hover")
	}, function() {
		$(this).find("div").removeClass("hover")
	})
};
function addMenu(topMenu,subMenuURI,basePath){
	topMenu.html("<div class='panel-loading'>Loading...</div>");
	$.myAjax.excuteAjaxMask({
		url:subMenuURI,
		reqData:"parentId="+topMenu.attr("menuId"),
		dataType: "json",
		successfn: function(data){
		     var htmlStr="<ul>"; 
		     if(data){
		          $.each(data,function (i,val){
		             htmlStr=htmlStr+"<li src='"+basePath+val.linkAddress+"' class='cs-navi-tab'><div><span class='icon'></span><a>"+val.menuName+"</a></div></li>";
		         });
		     }else{
		            $.messager.alert("消息", "解析菜单数据失败!", "error");
		     }
		      htmlStr=htmlStr+"</ul>";
		      topMenu.html(htmlStr);
		        // 注册事件
		       menuEvent();
		  }
	});
}
function colseTabsByTitle(subtitle){
	$("#tabs").tabs("close", subtitle);
}