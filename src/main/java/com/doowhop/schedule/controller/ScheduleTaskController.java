package com.doowhop.schedule.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doowhop.schedule.domain.ScheduleTask;
import com.doowhop.schedule.enums.RetCodeEnum;
import com.doowhop.schedule.enums.TaskModeEnum;
import com.doowhop.schedule.service.ScheduleTaskService;

/**
 * 定时任务管理控制器
 * 为了方便全部使用get方式;暂时只支持CronTask类型;并不严格遵守rest风格规范
 * @author Looop
 */
@RestController
@RequestMapping("scheduleTaskService")
public class ScheduleTaskController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ScheduleTaskService taskService;
	
	@GetMapping("/test1")
	void test1(){
		System.out.println("test1--------1-------");
	}
	@GetMapping("/test2")
	void test2(){
		System.out.println("test2--------2-------");
	}
	@GetMapping("/test3")
	void test3(){
		System.out.println("test3--------3-------");
	}
	
	/**添加任务
	 * url, method, cron 必填
	 * @param task 
	 * @return
	 */
	@PostMapping("/addTask")
	Map<String, Object> addTask(ScheduleTask task){
		Map<String, Object> retMap = new HashMap<String, Object>(4);
		retMap.put("retCode", RetCodeEnum.SUCCESS.getCode());				
		try {
			if(!checkParam(task)) {
				logger.info("ScheduleTaskController addTask Failed!");
				retMap.put("code", RetCodeEnum.FAILED.getCode());
				retMap.put("message", "参数缺失");
				return retMap;
			}
			boolean ret = taskService.addTask(task);	
			if(!ret) {
				logger.info("ScheduleTaskController addTask Failed!");
				retMap.put("code", RetCodeEnum.FAILED.getCode());
				retMap.put("message", "添加失败");
				return retMap;
			}
		} catch (Exception e) {
			logger.error("ScheduleTaskController addTask has a Exception: {}", e);
			retMap.put("code", RetCodeEnum.FAILED.getCode());
			retMap.put("message", "系统异常");
		}
		return retMap;
	}
	
	/**暂停任务,可以重新启动
	 * @param task
	 * @return
	 */
	@PostMapping("/stopTask")
	Map<String, Object> stopTask(long taskId) {
		Map<String, Object> retMap = new HashMap<String, Object>(4);
		retMap.put("retCode", RetCodeEnum.SUCCESS.getCode());
		try {
			boolean ret = taskService.stopTask(taskId);
			if(!ret) {
				logger.info("ScheduleTaskController stopTask Failed!");
				retMap.put("code", RetCodeEnum.FAILED.getCode());
				retMap.put("message", "停止失败");
				return retMap;
			}
		} catch (Exception e) {
			logger.error("ScheduleTaskController stopTask has a Exception: {}", e);
			retMap.put("code", RetCodeEnum.FAILED.getCode());
			retMap.put("message", "系统异常");
		}
		return retMap;
	}

	
	/**启动任务
	 * @param task
	 * @return
	 */
	@PostMapping("/startTask")
	Map<String, Object> startTask(long taskId){
		Map<String, Object> retMap = new HashMap<String, Object>(4);
		retMap.put("retCode", RetCodeEnum.SUCCESS.getCode());
		try {
			boolean ret = taskService.startTask(taskId);
			if(!ret) {
				logger.info("ScheduleTaskController startTask Failed!");
				retMap.put("code", RetCodeEnum.FAILED.getCode());
				retMap.put("message", "启动失败");
				return retMap;
			}
		} catch (Exception e) {
			logger.error("ScheduleTaskController startTask has a Exception: {}", e);
			retMap.put("retCode", RetCodeEnum.FAILED.getCode());
			retMap.put("errorMsg", e);
		}
		return retMap;
	}
	
	
	  /**更新任务
     * @param task
     * @return
     */
	@PostMapping("/modifyTask")
    Map<String, Object> modifyTask(ScheduleTask task){
		Map<String, Object> retMap = new HashMap<String, Object>(4);
		retMap.put("retCode", RetCodeEnum.SUCCESS.getCode());
		try {
			if(StringUtils.isEmpty(task.getTaskId())) {
				logger.info("ScheduleTaskController modifyTask Failed!");
				retMap.put("code", RetCodeEnum.FAILED.getCode());
				retMap.put("message", "参数缺失");
				return retMap;
			}
			boolean ret = taskService.modifyTask(task);
			if(!ret) {
				logger.info("ScheduleTaskController modifyTask Failed!");
				retMap.put("code", RetCodeEnum.FAILED.getCode());
				retMap.put("message", "更改失败");
			}
		} catch (Exception e) {
			logger.error("ScheduleTaskController modifyTask has a Exception: {}", e);
			retMap.put("retCode", RetCodeEnum.FAILED.getCode());
			retMap.put("errorMsg", e);
		}			
		return retMap;
	}
    
	/**手动立即执行任务
	 * @return
	 */
	@PostMapping("/mannualTask")
	Map<String, Object> mannualTask(long taskId){
		Map<String, Object> retMap = new HashMap<String, Object>(4);
		retMap.put("retCode", RetCodeEnum.SUCCESS.getCode());
		try {
			taskService.manualExcuteTask(taskId);
		} catch (Exception e) {
			logger.error("startUp the Task[{}] has Exception: ", taskId, e);
			retMap.put("retCode", RetCodeEnum.FAILED.getCode());
			retMap.put("errorMsg", e);
		}
		return retMap;
	}
	
	
	/**参数校验
	 * @param taskInfo
	 * @return
	 */
	private boolean checkParam(ScheduleTask task){
		if(StringUtils.isEmpty(task.getTaskMode())) {
			return false;
		}
		if(TaskModeEnum.CRON_TASK.getMode().equals(task.getTaskMode())) {
			if(StringUtils.isEmpty(task.getCron())) {
				return false;
			}
		}
		if(StringUtils.isEmpty(task.getUrl()) || StringUtils.isEmpty(task.getMethod())){
			return false;
		}
		return true;
	}	

}
