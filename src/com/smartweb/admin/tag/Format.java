package com.smartweb.admin.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class Format extends TagSupport {
	String type;
	String value;

	public Format() {
		super();
	}
	
	public Format(String type, String value) {
		super();
		setType(type);
		setValue(value);
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

	public void setType(String type) {
		if(type==null) this.type = "";
		else this.type = type.trim();
	}
	
	public void setValue(String value) {
		if(value==null) this.value = "";
		else this.value = value.trim();
	}

	public String getPage(){
		String ret = "";
		if("tel".equals(type)){
			ret = formatTel(value);
		}		
		return ret;
	}
	
	public String formatTel(String text){
		String ret = "";

		if(text.length()<7){
			ret = text;
		}
		else if(text.length()==7){
			ret = text.substring(0,3) + "-" + text.substring(3);
		}		
		else if(text.length()==8){
			ret = text.substring(0,4) + "-" + text.substring(4);
		}
//		else if(text.length()==9){
//			ret = text.substring(0,2) + "-" + text.substring(2,6) + "-" + text.substring(6);
//		}
//		else if(text.length()==10){
//			ret = text.substring(0,3) + "-" + text.substring(3,6) + "-" + text.substring(6);
//		}
//		else if(text.length()==11){
//			ret = text.substring(0,3) + "-" + text.substring(3,7) + "-" + text.substring(7);
//		}	
//		else if(text.length()>=12){
//			ret = text.substring(0,4) + "-" + text.substring(4,8) + "-" + text.substring(8);
//		}	
		else if(text.length()>8){
			if(text.substring(0,2).equals("02")){
				if(text.length()==9){
					ret = text.substring(0,2) + "-" + text.substring(2,5) + "-" + text.substring(5);
				}else if(text.length()>=10){
					ret = text.substring(0,2) + "-" + text.substring(2,6) + "-" + text.substring(6);
				}
				
			}else{
				if(text.length()==9){
					ret = text.substring(0,3) + "-" + text.substring(3,6) + "-" + text.substring(6);
				}
				else if(text.length()==10){
					ret = text.substring(0,3) + "-" + text.substring(3,6) + "-" + text.substring(6);
				}
				else if(text.length()==11){
					ret = text.substring(0,3) + "-" + text.substring(3,7) + "-" + text.substring(7);
				}	
				else if(text.length()>=12){
					ret = text.substring(0,4) + "-" + text.substring(4,8) + "-" + text.substring(8);
				}	
			}
		}
		return ret;
	}
}

