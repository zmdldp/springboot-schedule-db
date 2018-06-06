package com.doowhop.schedule.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.doowhop.schedule.service.ScheduleTaskService;

@Component
public class StartUpRunner implements ApplicationRunner {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ScheduleTaskService taskService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("启动后初始化定时任务...");
		taskService.initTask();
	}

}
