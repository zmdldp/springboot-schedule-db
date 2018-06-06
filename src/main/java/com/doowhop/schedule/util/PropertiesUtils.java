package com.doowhop.schedule.util;
/*package com.allinpal.schedule.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PropertiesUtil {
	
	@Autowired
    private Environment env;
	
	public List<Map<String, String>> readProperties(){
				
		List<Map<String, String>> reqList = new ArrayList<Map<String, String>>();
		for(int i=1;;i++){
			Map<String, String> reqMap = new HashMap<String, String>();
			String cron = env.getProperty("resttask"+i+".cron");
			String method = env.getProperty("resttask"+i+".method");
			String url = env.getProperty("resttask"+i+".url");
			
			if(cron==null || url==null || method==null) break;
			reqMap.put("cron", cron.trim());
			reqMap.put("url", url.trim());
			reqMap.put("method", method.trim());
			reqList.add(reqMap);
		}		
		System.out.println(reqList);	
		return reqList;
	}

}
*/