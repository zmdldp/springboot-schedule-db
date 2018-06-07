package com.doowhop.schedule.aop;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.doowhop.schedule.domain.ScheduleTask;
import com.doowhop.schedule.service.ScheduleTaskService;

@Aspect
@Component
public class AspectScheduleTaskRun {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ScheduleTaskService scheduleTaskService;
	
	  //申明一个切点 里面是 execution表达式
	  @Pointcut("execution(public * com.doowhop.schedule.task.RequestSender.sendRequest(..))")
	  private void runAspect() {
	  }
	  
	  @Around(value = "runAspect()")
	  public void methodAround(ProceedingJoinPoint  pjp) {
		  logger.info("更新调度任务运行状态和时间! {}", pjp.getSignature());
		  try {  
			  ScheduleTask task = (ScheduleTask) pjp.getArgs()[0];	
			  ScheduleTask updTask = new ScheduleTask();
			  updTask.setTaskId(task.getTaskId());
			  updTask.setProcState("01");//调度执行状态 00-执行完成(等待执行) 01-执行中'
			  updTask.setLastExecuteBegin(new Date());
			  updTask.setLastExecuteEnd(null);
			  scheduleTaskService.updateTaskById(updTask);
			  
			  Object o =  pjp.proceed();  
			  
			  updTask.setProcState("00");//调度执行状态 00-执行完成(等待执行) 01-执行中'
			  updTask.setLastExecuteEnd(new Date());
			  scheduleTaskService.updateTaskById(updTask);
	        } catch (Throwable e) {  
	        	logger.error("AspectScheduleTaskRun methodAround has a Exception!: {}", e);
	        }  
	  }
}
