package com.smartweb.admin.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartweb.admin.common.Util;


@Controller
@SuppressWarnings({"rawtypes","unused"})
public class LoginCtr {
	
	@Autowired
	private LoginBiz loginBiz;
	
	@RequestMapping(value="/login/login.do", method=RequestMethod.GET)
	public String login(@RequestParam HashMap<String, String> prm, Model model){
		
		return "/login/login";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/login/loginProc.do", method=RequestMethod.POST)
	public String loginProc(@RequestParam HashMap<String,String> prm, HttpServletRequest request, Model model){
		/* 응답코드 정의
		 * A : 로그인 성공
		 * B : 사용중지 계정
		 * C : 로그인 실패
		 * D : 미사용계정
		 * E : 계정상태 오류
		 * F : 입력값 오류
		 */
		String returnCode = "";
		
		if(!(Util.isNullOrSpace(prm.get("ADM_ID"))) && !(Util.isNullOrSpace(prm.get("ADM_PWD")))){
			prm.put("LOGIN_IP", request.getRemoteAddr());
			System.out.println("ip : "+prm.get("LOGIN_IP"));
			
			returnCode = loginBiz.selectAdmin(prm);
			if(Util.isNullOrSpace(returnCode)) returnCode="C";
		}
		else{
			//입력값 오류
			returnCode="F";
		}
		return returnCode;
	}
}
