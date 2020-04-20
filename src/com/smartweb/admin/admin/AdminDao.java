package com.smartweb.admin.admin;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

@SuppressWarnings("rawtypes")
public class AdminDao extends SqlMapClientDaoSupport{
	
	private final String PACKAGE_PATH = "com.smartweb.admin.admin.";
	
	/**
	 * 관리자 계정정보 총 수
	 */
	public int selectAdminCnt(Map map){
		return (Integer) getSqlMapClientTemplate().queryForObject(PACKAGE_PATH+"selectAdminCnt", map);
	}
	
	/**
	 * 관리자 계정정보 리스트
	 */
	public List selectAdminList(Map map){
		return (List) getSqlMapClientTemplate().queryForList(PACKAGE_PATH+"selectAdminList", map);
	}
	
}
