package com.cpapp.common.base.bean;

/*******************************************************************************
 * 基础查询参数bean
 * 
 * @author zengxiangtao
 * @version 2013-11-18
 * @param <T>
 ******************************************************************************/
public class QueryParamBean<T> extends Pagination<T> {
	// 状态
	private String searchStatus;
	// 起始时间
	private String startDate;
	// 截止时间
	private String endDate;

	public String getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "QueryParamBean [searchStatus=" + searchStatus + ", startDate="
				+ startDate + ", endDate=" + endDate + ", getTotalPage()="
				+ getTotalPage() + ", getFromRowNum()=" + getFromRowNum()
				+ ", getEndRowNum()=" + getEndRowNum() + ", getTotalRows()="
				+ getTotalRows() + ", getPageSize()=" + getPageSize()
				+ ", getToPage()=" + getToPage() + "]";
	}
	
	
}
