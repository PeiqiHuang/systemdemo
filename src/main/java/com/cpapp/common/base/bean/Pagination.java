package com.cpapp.common.base.bean;

import java.util.List;

/*******************************************************************************
 * 分页Bean
 * 
 * @author zengxiangtao
 * @version 2013-11-18
 ******************************************************************************/
public class Pagination<T> {
	// 总记录数
	private int totalRows = 0;
	// 页大小
	private int pageSize = 20;
	// 请求页
	private int toPage = 1;
	// 查询结果集
	private List<T> resultLists;

	/*
	 * 总页数
	 */
	public int getTotalPage() {
		if (totalRows > 0) {
			int totalpage = totalRows / pageSize;
			if (totalRows % pageSize != 0) {
				totalpage++;
			}
			return totalpage;
		}
		return 0;
	}

	// 起始行
	public int getFromRowNum() {
		return (this.getToPage() - 1) * this.pageSize;
	}

	// 结束行
	public int getEndRowNum() {
		return this.getToPage() * this.pageSize;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getToPage() {
		return toPage;
	}

	public void setToPage(int toPage) {
		this.toPage = toPage;
	}

	public List<T> getResultLists() {
		return resultLists;
	}

	public void setResultLists(List<T> resultLists) {
		this.resultLists = resultLists;
	}
}
