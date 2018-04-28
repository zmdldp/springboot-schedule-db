package com.doowhop.schedule.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doowhop.schedule.domain.TaskInfo;
import com.doowhop.schedule.enums.RetCodeEnum;
import com.doowhop.schedule.service.TaskService;

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
	private TaskService taskService;
	
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
	
	/**初始化任务列表
	 * 
	 * @return
	 */
	@GetMapping("/init")
	Map<String, Object> init(){
		Map<String, Object> retMap = new HashMap<String, Object>(4);
		retMap.put("retCode", RetCodeEnum.SUCCESS.getType());		
		try {			
			taskService.init();				
		} catch (Exception e) {
			logger.error("init Tasks has Exception: ", e);
			retMap.put("retCode", RetCodeEnum.FAILED.getType());
			retMap.put("errorMsg", e);
		}		
		return retMap;		
	}
	
	/**添加任务
	 * url, method, cron 必填
	 * @param task 
	 * @return
	 */
	@GetMapping("/add")
	Map<String, Object> add(TaskInfo taskInfo){
		Map<String, Object> retMap = new HashMap<String, Object>(4);
		retMap.put("retCode", RetCodeEnum.SUCCESS.getType());				
		try {
			taskService.add(taskInfo);		
		} catch (Exception e) {
			logger.error("add the Task[{}] has Exception: ", taskInfo.getUrl(), e);
			retMap.put("retCode", RetCodeEnum.FAILED.getType());
			retMap.put("errorMsg", e);
		}
		return retMap;
	}
	
	/**暂停任务,可以重新启动
	 * @param task
	 * @return
	 */
	@GetMapping("/stop")
	Map<String, Object> stop(long taskId) {
		Map<String, Object> retMap = new HashMap<String, Object>(4);
		retMap.put("retCode", RetCodeEnum.SUCCESS.getType());
		try {
			taskService.stop(taskId);		
		} catch (Exception e) {
			logger.error("stop the Task[{}] has Exception: ", taskId, e);
			retMap.put("retCode", RetCodeEnum.FAILED.getType());
			retMap.put("errorMsg", e);
		}
		return retMap;
	}
	
	/**暂停所有任务,可以重新启动
	 * @param task
	 * @return
	 */
	@GetMapping("/stopAll")
	Map<String, Object> stopAll() {
		Map<String, Object> retMap = new HashMap<String, Object>(4);
		retMap.put("retCode", RetCodeEnum.SUCCESS.getType());
		try {
			taskService.stopAll();			
		} catch (Exception e) {
			logger.error("stopAll tasks has Exception: ", e);
			retMap.put("retCode", RetCodeEnum.FAILED.getType());
			retMap.put("errorMsg", e);
		}
		return retMap;
	}

	
	/**启动任务
	 * @param task
	 * @return
	 */
	@GetMapping("/start")
	Map<String, Object> start(long taskId){
		Map<String, Object> retMap = new HashMap<String, Object>(4);
		retMap.put("retCode", RetCodeEnum.SUCCESS.getType());
		try {
			taskService.start(taskId);
		} catch (Exception e) {
			logger.error("start the Task[{}] has Exception: ", taskId, e);
			retMap.put("retCode", RetCodeEnum.FAILED.getType());
			retMap.put("errorMsg", e);
		}
		return retMap;
	}
	
    /**删除任务
     * @param task
     * @return
     */
	@GetMapping("/delete")
    Map<String, Object> delete(long taskId){
		Map<String, Object> retMap = new HashMap<String, Object>(4);
		retMap.put("retCode", RetCodeEnum.SUCCESS.getType());
		try {
			taskService.delete(taskId);
		} catch (Exception e) {
			logger.error("delete the Task[{}] has Exception: ", taskId, e);
			retMap.put("retCode", RetCodeEnum.FAILED.getType());
			retMap.put("errorMsg", e);
		}
		return retMap;
	}
	
	  /**更新任务
     * @param task
     * @return
     */
	@GetMapping("/update")
    Map<String, Object> update(TaskInfo taskInfo){
		Map<String, Object> retMap = new HashMap<String, Object>(4);
		retMap.put("retCode", RetCodeEnum.SUCCESS.getType());
		try {
			taskService.update(taskInfo);
		} catch (Exception e) {
			logger.error("reset the Task[{}] has Exception: ", taskInfo.getTaskId(), e);
			retMap.put("retCode", RetCodeEnum.FAILED.getType());
			retMap.put("errorMsg", e);
		}			
		return retMap;
	}
    
    /**重置任务
     * @param task
     * @return
     */
	@GetMapping("/reset")
    Map<String, Object> reset(long taskId){
		Map<String, Object> retMap = new HashMap<String, Object>(4);
		retMap.put("retCode", RetCodeEnum.SUCCESS.getType());
		try {
			taskService.reset(taskId);
		} catch (Exception e) {
			logger.error("reset the Task[{}] has Exception: ", taskId, e);
			retMap.put("retCode", RetCodeEnum.FAILED.getType());
			retMap.put("errorMsg", e);
		}			
		return retMap;
	}
	
	/**手动立即执行任务
	 * @return
	 */
	Map<String, Object> startUp(long taskId){
		Map<String, Object> retMap = new HashMap<String, Object>(4);
		retMap.put("retCode", RetCodeEnum.SUCCESS.getType());
		try {
			taskService.startUp(taskId);
		} catch (Exception e) {
			logger.error("startUp the Task[{}] has Exception: ", taskId, e);
			retMap.put("retCode", RetCodeEnum.FAILED.getType());
			retMap.put("errorMsg", e);
		}
		return retMap;
	}

}
