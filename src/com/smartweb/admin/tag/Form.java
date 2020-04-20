package com.smartweb.admin.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class Form extends TagSupport {

	String id;
	String method;
	String action;
	HashMap<String, String> map;
	
	

	public void setId(String id) {
		this.id = id;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().print(getPage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
	
	private String getPage(){
		String ret = "";
		ret += "<";
		ret += "form";
		if(id!=null) ret += " id='"+id+"'";
		if(method!=null) ret += " method='"+method+"'";
		if(action!=null) ret += " action='"+action+"'";
		ret += ">";
		if(map!=null){
			Iterator<String> iter = map.keySet().iterator();
			while(iter.hasNext()){
				String name = iter.next();
				String value = map.get(name);
				if(value!=null && !"".equals(value)){
					ret += "<input type='hidden' name='"+name+"' value='"+value+"'/>";
				}
			}
		}		
		ret += "</form>";
		
		return ret;
	}	
}