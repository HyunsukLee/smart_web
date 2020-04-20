package com.smartweb.admin.admin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartweb.admin.common.Paging;


@Controller
@SuppressWarnings({"rawtypes","unused"})
public class AdminCtr {
	protected final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private AdminBiz adminBiz;
	
	@RequestMapping(value="/admin/admin_list.do",method=RequestMethod.GET)
	public String admin_list(@RequestParam HashMap<String, String> prm, HttpServletRequest request, HttpSession session, Model model){
		int adm_seq = Integer.parseInt((String)session.getAttribute("adm_seq")); //관리자 번호
		int chiefTP = Integer.parseInt((String) session.getAttribute("adm_type")); //관리자 상태
		
//		System.out.println("chiefTP : " + chiefTP);
		String chiefYN = "N";
		if(chiefTP==99){
			chiefYN = "Y";
		}else{
			chiefYN = "N";
		}
		
		//기본 검색 기간
		Calendar cal = Calendar.getInstance();
		String def_end_date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		cal.add(Calendar.MONTH, -1); //한달전
		String def_start_date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		
		//default value
		if(prm.get("ORDER_BY")==null) prm.put("ORDER_BY", "ADM_SEQ");
		if(prm.get("ORDER_SC")==null) prm.put("ORDER_SC", "DESC");
		
		//전체건수
		int total = adminBiz.selectAdminCnt(prm);
		logger.debug("total="+total);
		
		//페이지 세팅
		Paging.set(prm); 
		logger.debug("parameter="+prm);
		
		List list = adminBiz.selectAdminList(prm);
		
		int displayCount=0;
		if(list.size()==0){
			int dc = 20;
			if(prm.get("displayCount")!=null) dc=Integer.parseInt(prm.get("displayCount"));
			displayCount=dc;
		}else{
			displayCount = list.size();
		}
		
		model.addAttribute("chiefYN", chiefYN);
		model.addAttribute("displayCount", displayCount);
		model.addAttribute("total", total);
		model.addAttribute("prm", prm);
		model.addAttribute("list", list);
		
		return "/admin/admin_list";
	}
	
}
