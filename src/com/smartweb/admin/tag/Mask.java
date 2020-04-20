package com.smartweb.admin.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class Mask extends TagSupport {
	String type;
	String value;
	
	public void setType(String type) {
		if(type==null) this.type = "";
		else this.type = type.trim();
	}

	public void setValue(String value) {
		if(value==null) this.value = "";
		else this.value = value.trim();
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
		if("tel".equals(type)){
			ret = maskTel(value);
		}
		else if("email".equals(type)){
			ret = maskEmail(value);
		}else if("name".equals(type)){
			ret = maskName(value);
		}else if("id".equals(type)){
			ret = maskId(value);
		}
		return ret;
	}
	
	private String maskTel(String text){
		String ret = "";

		if(text.length()==0){
			ret = "";
		}
		else if(text.length()<7){
			ret = "****";
		}
		else if(text.length()==7){
			ret = "***" + "-" + text.substring(3);
		}		
		else if(text.length()==8){
			ret = "****" + "-" + text.substring(4);
		}
		else if(text.length()>8){

			if(text.substring(0,2).equals("02")){
				if(text.length()==9){
					ret = text.substring(0,2) + "-" + "***" + "-" + text.substring(5);
				}else if(text.length()>=10){
					ret = text.substring(0,2) + "-" + "****" + "-" + text.substring(6);
				}
				
			}else{
				if(text.length()==9){
					ret = text.substring(0,3) + "-" + "***" + "-" + text.substring(6);
				}
				else if(text.length()==10){
					ret = text.substring(0,3) + "-" + "***" + "-" + text.substring(6);
				}
				else if(text.length()==11){
					ret = text.substring(0,3) + "-" + "****" + "-" + text.substring(7);
				}	
				else if(text.length()>=12){
					ret = text.substring(0,4) + "-" + "****" + "-" + text.substring(8);
				}
			}
		}
		return ret;		
	}

	private String maskName(String text){
		String ret = "";

		if(text.length()==2){
			ret = text.substring(0,1) + "*";
		}else if(text.length()>2){
			ret = text.substring(0,1);
			for(int i=0; i<text.length()-2; i++){
				ret += "*";
			}
			ret += text.substring(text.length()-1);
		}
		
		return ret;		
	}
	
	private String maskId(String text){
		String ret = "";

		if(text.length()>2){
			ret = text.substring(0,text.length()-2) + "**";
		}else if(text.length()<3){
			ret = "**";
		}
		
		return ret;		
	}
	
	private String maskEmail(String text){
		String ret = "";

		if(text.length()>2){
			int at = text.indexOf("@");
			if(at>-1){
				ret = maskId(text.substring(0, at)) + text.substring(at);
			}else{
				ret = maskId(text);
			}
		}
		
		return ret;		
	}	
}

