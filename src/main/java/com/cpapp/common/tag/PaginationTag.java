package com.cpapp.common.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.cpapp.common.base.bean.Pagination;

/***
 * 列表表格的页尾标签
 * 
 * @author zengxiangtao
 * */
@SuppressWarnings("rawtypes")
public class PaginationTag extends TagSupport {
	private static final long serialVersionUID = -120403566912315471L;
	
	private String colspan;
	private String pageFormId;
	private Pagination pagination;

	public int doStartTag() {
		// 用pageContext获取out，他还能获取session等，基本上jsp的内置对象都能获取
		JspWriter out = pageContext.getOut();
		try {
			out.println("<tr class='ListTablePager'><td colspan='"
					+ getColspan() + "'>");
			out.println("<table cellspacing='4' cellpadding='0' border='0'><tr><td>");
			out.print("<span class='pagination-first' title='首页' onclick='gotoPage(this)'  topage='1' pf='"
					+ getPageFormId()
					+ "' nowpage='"
					+ getPagination().getToPage() + "'></span>");
			out.print("<span class='pagination-prev' title='上一页' onclick='gotoPage(this)' topage='"
					+ ((getPagination().getToPage() - 1) < 1 ? "1"
							: getPagination().getToPage() - 1)
					+ "' pf='"
					+ getPageFormId()
					+ "' nowpage='"
					+ getPagination().getToPage() + "'></span>");
			out.print("<span class='pagination-next' title='下一页' onclick='gotoPage(this)' topage='"
					+ ((getPagination().getToPage() + 1) > getPagination()
							.getTotalPage() ? getPagination().getTotalPage()
							: getPagination().getToPage() + 1)
					+ "' pf='"
					+ getPageFormId()
					+ "' nowpage='"
					+ getPagination().getToPage() + "'></span>");
			out.print("<span class='pagination-last' title='末页' onclick='gotoPage(this)' topage='"
					+ getPagination().getTotalPage()
					+ "' pf='"
					+ getPageFormId()
					+ "' nowpage='"
					+ getPagination().getToPage() + "'></span>");
			out.print("</td><td><select title='选择页码' onchange='changePageSize(this)' pf='"
					+ getPageFormId() + "'>");
			out.print("<option "
					+ (getPagination().getPageSize() == 10 ? "selected='true'"
							: "") + ">10</option>");
			out.print("<option "
					+ (getPagination().getPageSize() == 20 ? "selected='true'"
							: "") + ">20</option>");
			out.print("<option "
					+ (getPagination().getPageSize() == 30 ? "selected='true'"
							: "") + ">30</option>");
			out.print("<option "
					+ (getPagination().getPageSize() == 50 ? "selected='true'"
							: "") + ">50</option>");
			out.print("<option "
					+ (getPagination().getPageSize() == 50 ? "selected='true'"
							: "") + ">100</option>");
			out.println("</select></td><td>共" + getPagination().getTotalRows()
					+ "条记录,当前" + getPagination().getToPage() + "/"
					+ getPagination().getTotalPage() + "页</td>");
			out.println("</tr></table></td></tr>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() {
		return SKIP_BODY;
	}

	public String getPageFormId() {
		return pageFormId;
	}

	public void setPageFormId(String pageFormId) {
		this.pageFormId = pageFormId;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public String getColspan() {
		return colspan;
	}

	public void setColspan(String colspan) {
		this.colspan = colspan;
	}
}
