package com.smartweb.admin.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class Paging extends TagSupport {
	
	private int page = 1;
	private int total = 1;
	private int list_size = 10;
	private int page_size = 10;
	
	@Override
	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().println(getPage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
	
	public void setPage(int page) {
		this.page = page;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setList_size(int list_size) {
		this.list_size = list_size;
	}
	
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	private String getPage(){
		String ret = "";

		if(page<1) page=1;
		if(total<1) return "";
		
		int currentFirst = (page-1)/page_size;		
		currentFirst = currentFirst * page_size + 1;
		int currentlast = (page-1)/page_size;		
		currentlast = currentlast * page_size + page_size;
		
		int nextFirst = (page-1)/page_size;		
		nextFirst = (nextFirst+1) * page_size + 1;
		int prevLast = (page-1)/page_size; 		
		prevLast = (prevLast-1) * page_size + page_size;
		
		int lastPage = 1;
		lastPage = total / list_size;
		
		if ( total%list_size != 0 ) lastPage = lastPage + 1;
		currentlast = (currentlast>lastPage)?lastPage:currentlast;
		
		ret += " <div class='paginate mt10'> ";
		if ( page>1 ) {
			ret += " <a href=\"javascript:goPage('1')\"><span><img src='../images/common/btn_paginate_first.gif' alt='처음' /></span></a> ";
		}
		else{
			ret += " <span><img src='../images/common/btn_paginate_first.gif' alt='처음' /></span> ";
		}
		
		if ( prevLast > 0 ) {
			ret += " <a href=\"javascript:goPage('"+prevLast+"');\"><span><img src='../images/common/btn_paginate_prev.gif'  alt='이전' /></span></a> ";
		}
		else{
			ret += " <span><img src='../images/common/btn_paginate_prev.gif'  alt='이전' /></span> ";
		}

		for (int j=currentFirst; j<currentFirst+page_size && j<=lastPage; j++) {	
			if ( j <= currentlast ) {
				if ( j == page ) {
					ret += " <a href='#' class='on'>"+j+"</a> ";
				} else {
					ret += " <a href=\"javascript:goPage('"+j+"');\">"+j+"</a> ";
				}
			} 
		}

		if ( nextFirst <= lastPage ) {
			ret += " <a href=\"javascript:goPage('"+nextFirst+"')\"><span><img src='../images/common/btn_paginate_next.gif' alt='다음' /></span></a> ";
		}
		else{
			ret += " <span><img src='../images/common/btn_paginate_next.gif' alt='다음' /></span> ";
		}
		
		if ( page<lastPage ) {
			ret += " <a href=\"javascript:goPage('"+lastPage+"')\"><span><img src='../images/common/btn_paginate_last.gif' alt='마지막' /></span></a> ";
		}
		else{
			ret += " <span><img src='../images/common/btn_paginate_last.gif' alt='마지막' /></span> ";
		}
		ret += " </div> ";
		
		return ret;
	}
	
}

