package com.doowhop.schedule.task;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.doowhop.schedule.domain.ScheduleTask;
import com.doowhop.schedule.enums.ContentTypeEnum;
import com.doowhop.schedule.util.CollectionUtils;
import com.doowhop.schedule.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("requestSender")
public class RequestSender {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String sendRequest(ScheduleTask scheduleTask) {
		String ret = null;
		String method = scheduleTask.getMethod();
		String url = scheduleTask.getUrl();
		String param = scheduleTask.getParam();
		String contentType = scheduleTask.getContentType();
		try {
			logger.info("-----定时调度任务[{}]开始-----", url);
			if(Constants.POST_METHOD.equals(method)) {			
				if(!StringUtils.isEmpty(param)){
					if(ContentTypeEnum.JSON.getType().equals(contentType)) {
						ret = restTemplate.postForObject(url, param, String.class);
					}else if(ContentTypeEnum.URLENCODED.getType().equals(contentType)){
						MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();				
						@SuppressWarnings("unchecked")
						Map<String, String> map = JSONObject.parseObject(param, Map.class);
						paramMap = CollectionUtils.convertMap2MultiValueMap(map);
						ret = restTemplate.postForObject(url, paramMap, String.class);
					}else {
						logger.error("未知的method:{}!", method);
					}
				}else {
					ret = restTemplate.postForObject(url, null, String.class);
				}					
			} else if(Constants.GET_METHOD.equals(scheduleTask.getMethod())) {
				ret = restTemplate.getForObject(url, String.class);
			} else {
				logger.error("未知的contentType:{}!", contentType);
			}			
		} catch (Exception e) {
			logger.error("-----定时任务发生异常-----", e);	
		}
		logger.info("-----定时任务[{}]结束, result:{}-----", url, ret);	
		return ret;
	}	
}
