package com.doowhop.schedule.util;

import java.util.Map;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class CollectionUtils {
	
	
	
	/**convert Map to MultiValueMap
	 * @param map
	 * @return
	 */
	public static MultiValueMap<String, String> convertMap2MultiValueMap(Map<String, String> map){
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
		for(Map.Entry<String, String> entry :map.entrySet()){
			multiValueMap.add(entry.getKey(), entry.getValue());
		}
		return multiValueMap;		
	}

}
