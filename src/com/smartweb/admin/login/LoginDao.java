package com.smartweb.admin.login;

import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

@SuppressWarnings("rawtypes")
public class LoginDao extends SqlMapClientDaoSupport{

	private final String PACKAGE_PATH = "com.smartweb.admin.login.";
	
	/**
	 * 회원 계정정보
	 */
	public Map selectAdmin(Map map){
		return (Map) getSqlMapClientTemplate().queryForObject(PACKAGE_PATH+"selectAdmin", map);
	}
	
	/**
	 * 로그인 실패횟수, 계정상태 업데이트 
	 */
	public int updateAdmin(Map map){
		return (Integer) getSqlMapClientTemplate().update(PACKAGE_PATH+"updateAdmin", map);
	}	

	/**
	 * 로그인 로그
	 */
	public String insertLogin(Map map){
		return (String)getSqlMapClientTemplate().insert(PACKAGE_PATH+"insertLogin", map);
	}
	
	/**
	 * 로그아웃 로그
	 */
	public int updateLogin(String login_seq){
		return (Integer)getSqlMapClientTemplate().update(PACKAGE_PATH+"updateLogin", login_seq);
	}	
}
