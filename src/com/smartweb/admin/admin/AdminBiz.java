package com.smartweb.admin.admin;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("rawtypes")
public class AdminBiz {
	protected final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private AdminDao adminDao;
	

	public int selectAdminCnt(Map map){
		return adminDao.selectAdminCnt(map);
	}
	
	public List selectAdminList(Map map){
		return adminDao.selectAdminList(map);
	}

}
