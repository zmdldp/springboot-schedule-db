package com.doowhop.schedule.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.doowhop.schedule.util.CollectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service("scheduleService")
public class ScheduleService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Runnable getTaskRunnable(final String url, final String method, final String param){
		
		return new Runnable() {			
			@Override
			public void run() {
				if("post".equals(method.trim().toLowerCase())){
					reqForPost(url, param);
				}else{
					reqForGet(url);
				}				
			}
		};
		
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Boolean reqForPost(String url, String paramJson){
		
		try {
			MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
			if(!StringUtils.isEmpty(paramJson)){
				Map map = new ObjectMapper().readValue(paramJson, Map.class);
				paramMap = CollectionUtils.convertMap2MultiValueMap(map);
			}
			logger.info("-----定时任务{}开始-----", url);
			Object obj = restTemplate.postForObject(url, paramMap, String.class);
			logger.info("-----定时任务{}结束, result:{}-----", url, obj);
			return true;
		} catch (Exception e) {		
			logger.error("-----定时任务发生异常-----", e);
			return false;	
		}			
	}
	
	
	public Boolean reqForGet(String url){
		
		try {
			logger.info("-----定时任务{}开始-----", url);
			Object obj = restTemplate.getForObject(url, String.class);
			logger.info("-----定时任务{}结束, result:{}-----", url, obj);
			return true;
		} catch (Exception e) {			
			logger.error("-----定时任务发生异常-----", e);
			return false;	
		}
	}
}
