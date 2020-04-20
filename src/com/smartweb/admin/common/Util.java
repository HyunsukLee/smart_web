package com.smartweb.admin.common;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Util {
	
	public static void setResponseMessage(HttpServletResponse response, String msg) {
		setResponseHtmlMessageScript(response, null, msg, null);
	}
	
	public static void setResponseScript(HttpServletResponse response, String script) {
		setResponseHtmlMessageScript(response, null, null, script);
	}
	
	public static void setResponseMessageScript(HttpServletResponse response, String msg, String script) {
		setResponseHtmlMessageScript(response, null, msg, script);
	}
	public static void setResponseHtmlMessageScript(HttpServletResponse response, String html, String msg, String script) {
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Content-Type", "text/html; charset=utf-8");
		
		try {
			ServletOutputStream out = response.getOutputStream();
			out.print("<html>");
			if ( html != null && !"".equals(html.trim())) {
				out.print(new String(html.getBytes("utf-8"), "ISO_8859_1"));
			}
			out.print("<script language='javascript'>");
			if ( msg != null && !"".equals(msg.trim())) {
				out.print("alert(\"" + new String(msg.getBytes("utf-8"), "ISO_8859_1") + "\");");
			}
			if ( script != null && !"".equals(script.trim())) {
				out.print(new String(script.getBytes("utf-8"), "ISO_8859_1"));
			}
			out.print("</script>");
			out.print("</html>");
			out.flush();
		} catch ( Exception e ) {
			System.out.println(e);
		}
	}
	
	/**
	null이거나 공백일때 true를 반환하다.
	Parameter Parsing이나 DB에서 데이터를 읽어올 때 유용하게 쓰일 것이다.

	@param str null인지 공백인지 확인하고 싶은 String 값
	@return null이거나 공백이면 true반환, 아니면 false 반환
   */
	public static boolean isNullOrSpace(String str) {

		if (str == null || str.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static int nvl(String text){
		return nvl(text, 0);
	}
	
	public static int nvl(String text, int def){
		int ret = def;
		try{
			ret = Integer.parseInt(text);
		}catch(Exception e){
			ret = def;
		}
		return ret;
	}
	
	public static String nvl(Object text, String def){
		if(text==null || "".equals(text.toString().trim())){
			return def;
		}else{
			return text.toString();
		}
	}
	
	public static String getRootUrl(HttpServletRequest request){
		String ret = "";
		
		ret += request.getScheme() + "://" + request.getServerName();
		if(request.getLocalPort()!=80){
			ret += ":"+request.getLocalPort();
		}
		ret += "/";
		
		return ret;
	}
}
