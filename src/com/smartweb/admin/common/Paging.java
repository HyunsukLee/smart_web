package com.smartweb.admin.common;

import java.util.Map;

public class Paging {
	public static void set(Map<String, String> prm){
		int page = Util.nvl( prm.get("page"), 1 );
		int pageSize = Util.nvl( prm.get("pageSize"), 20 );
		
		if(prm.get("page")==null) prm.put("page", page+"");
		if(prm.get("pageSize")==null) prm.put("pageSize", pageSize+"");
		
		int start_row = (page-1)*pageSize +1;
		int end_row = (page-1)*pageSize+pageSize;
		
		prm.put("START_ROW", start_row+"");
		prm.put("END_ROW", end_row+"");
	}
}
