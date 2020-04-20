package com.smartweb.admin.login;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.smartweb.admin.common.Adminsha;

@Service
@SuppressWarnings({"rawtypes","unused"})
public class LoginBiz {
	
	protected final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private LoginDao loginDao;
	
	public String selectAdmin(Map map){
		String retData="";
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		
		Map retMap = loginDao.selectAdmin(map);
		
		if(retMap!=null){
			String adm_seq = retMap.get("ADM_SEQ").toString();
			String adm_id = (String)retMap.get("ADM_ID");
			String adm_nm = (String)retMap.get("ADM_NM");
			String adm_sts = (String)retMap.get("ADM_STS");
			String adm_type = retMap.get("ADM_TYPE").toString();
			int fail_cnt = ((BigDecimal)retMap.get("FAIL_CNT")).intValue();
			
			System.out.println(adm_seq +" / " + adm_id +" / " + adm_nm +" / " + adm_sts +" / " + adm_type);
			
			//정상계정, 미사용 계정
			if("A".equals(adm_sts) || "D".equals(adm_sts)){
				//비밀번호 일치
				if( Adminsha.getshavalue((String)map.get("ADM_PWD")).equals((String)retMap.get("ADM_PWD")) ){
					retMap.put("FAIL_CNT", 0+"");
					updateAdmin(retMap);
					
					//로그인 세션 저장
					session.setAttribute("login_yn", "Y");
					session.setAttribute("adm_seq", adm_seq);
					session.setAttribute("adm_id", adm_id);
					session.setAttribute("adm_nm", adm_nm);
					session.setAttribute("adm_sts", adm_sts);
					session.setAttribute("adm_type", adm_type);
					
					//로그인 로그
					map.put("LOGIN_ID", adm_id);
					map.put("ADM_SEQ", adm_seq);

					String login_seq = insertLogin(map);
					System.err.println("lOGIN 번호 : " + login_seq);
					session.setAttribute("login_seq", login_seq);
					System.out.println(adm_sts);
					retData = adm_sts;
				}
				//비밀번호 불일치
				else{
					fail_cnt++;
					map.put("FAIL_CNT", fail_cnt+"");
					if(fail_cnt>=5) map.put("ADM_STS", "S");	//로그인 실패횟수 초과(사용중지)
					updateAdmin(map);
					retData = "C";
				}
			}
			//사용 중지 계정
			else if("B".equals(adm_sts)){
				retData = "B";
			}
			//계정상태 오류
			else{
				retData = "E";
			}
		}
		else{
			retData = "C";
		}
		return retData;
	}
	
	public int updateAdmin(Map map) {
		return loginDao.updateAdmin(map);
	}
	
	public String insertLogin(Map map){
		return loginDao.insertLogin(map);
	}	
	
	public int updateLogin(String login_seq){
		return loginDao.updateLogin(login_seq);
	}
		
}
