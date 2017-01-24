package com.cpapp.common.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

/*******************************************************************************
 * 配置项_描述展示_Tag
 * 
 * @author zengxiangtao
 ******************************************************************************/
public class SysCfgDescTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private String cfgKey;
	private Map<String, String> cfgMap;

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		JspWriter out = pageContext.getOut();
		try {
			if (StringUtils.isNotBlank(cfgKey)) {
				if (null == cfgMap) {
					cfgMap = (Map<String, String>) pageContext
							.getAttribute("cfgMap");
				}
				out.print(null == cfgMap ? cfgKey : cfgMap.get(cfgKey));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() {
		return SKIP_BODY;
	}

	public String getCfgKey() {
		return cfgKey;
	}

	public void setCfgKey(String cfgKey) {
		this.cfgKey = cfgKey;
	}

	public Map<String, String> getCfgMap() {
		return cfgMap;
	}

	public void setCfgMap(Map<String, String> cfgMap) {
		this.cfgMap = cfgMap;
	}
}
