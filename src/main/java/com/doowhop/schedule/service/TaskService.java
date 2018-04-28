package com.doowhop.schedule.service;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.doowhop.schedule.ScheduleConfig;
import com.doowhop.schedule.domain.TaskInfo;
import com.doowhop.schedule.enums.TaskTypeEnum;
import com.doowhop.schedule.mapper.TaskMapper;

/**
 * 定时任务的动态管理
 * 定时任务表的增删改查
 * @author liudp
 *
 */
@Service("taskService")
public class TaskService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TaskMapper taskMapper;
	
	@Autowired
	private ScheduleConfig scheduleConfig;
	
	@Autowired
	private ScheduleService scheduleService;
	
	/**
	 * 初始化任务列表, 将数据库中任务添加到定时执行队列
	 */
	public void init(){
		List<TaskInfo> taskInfoList = this.getCronTasks();
		if(!CollectionUtils.isEmpty(taskInfoList)){
			for (TaskInfo taskInfo : taskInfoList) {
				long taskId = taskInfo.getTaskId();
				try {
					if(checkParam(taskInfo)){				
						scheduleConfig.addTask(taskId, this.taskFactory(taskInfo));
						//立即启动执行
						if("Y".equalsIgnoreCase(taskInfo.getIsImmediExe())){
							this.newThreadTaskStart(taskInfo);
						}
					}else{
						logger.error("the Task[{}] param lost.", taskId);
					}	
				} catch (Exception e) {
					logger.error("inner foreach has Exception.", e);
					continue;
				}					
			}	
		}
	}
	
	/**
	 * 动态填加任务到执行队列,同时更新任务表
	 * @param taskInfo
	 */
	public void add(TaskInfo taskInfo){
		
		if(checkParam(taskInfo)){
			logger.info("the Task[{}] add start.", taskInfo.getUrl());
			int taskCnt = this.getTasksCnt(taskInfo);
			if(taskCnt == 0){
				int addCnt = this.addTask(taskInfo);
				if(addCnt == 1){
					long taskId = this.getTaskId(taskInfo);
					Task task = taskFactory(taskInfo);	
					scheduleConfig.addTask(taskId, task);
					//立即启动执行
					if("Y".equalsIgnoreCase(taskInfo.getIsImmediExe())){
						this.newThreadTaskStart(taskInfo);
						}
				}else{
					logger.error("the Task[{}] insert to DB failed.", taskInfo.getUrl());
				}				
			}else{
				logger.error("the Task[{}] in DB is exist.", taskInfo.getUrl());
			}
		}else{
			logger.error("the Task[{}] param lost.", taskInfo.getUrl());
		}
	}
	
	
	/**暂停任务执行队列
	 * @param taskId
	 * @throws NoSuchFieldException 
	 */
	public void stop(long taskId) throws NoSuchFieldException{
		logger.info("the Task[{}] stop start.", taskId);
		if(scheduleConfig.hasTask(taskId)){
			scheduleConfig.cancelTask(taskId);
		}else{
			logger.error("the Task[{}] in springBoot not exist.", taskId);
		}	
	}
	
	
	/**
	 * 暂停所有定时任务的执行队列
	 * @throws NoSuchFieldException 
	 */
	public void stopAll() throws NoSuchFieldException{	
		logger.info("topAll tasks start.");
		Set<Object> taskIds = scheduleConfig.getRunningTaskIds();
		for (Object taskId : taskIds) {
			if(scheduleConfig.hasTask((long) taskId)){
				scheduleConfig.cancelTask((long) taskId);
			}else{
				logger.error("the Task[{}] in springBoot not exist.", taskId);
			}	
		}	
	}
	
	/**启动一个被暂停的任务
	 * @param taskId
	 */
	public void start(long taskId){
		logger.info("the Task[{}] start start.", taskId);
		if(!scheduleConfig.hasTask(taskId)){
			TaskInfo taskInfo = this.getTaskById(taskId);
			if(taskInfo != null && checkParam(taskInfo)){
				scheduleConfig.addTask(taskId, taskFactory(taskInfo));
				//立即启动执行
				if("Y".equalsIgnoreCase(taskInfo.getIsImmediExe())){
					newThreadTaskStart(taskInfo);
				}
			}else{
				logger.error("the Task[{}] in DB not exist.", taskId);
			}		
		}else{
			logger.info("the Task[{}] in springBoot has been started.", taskId);
		}
	}
	
	
	/**从队列中删除一个任务,同时更新任务表
	 * @param taskId
	 * @throws NoSuchFieldException 
	 */
	public void delete(long taskId) throws NoSuchFieldException{		
		logger.info("the Task[{}] delete start.", taskId);
		if(scheduleConfig.hasTask(taskId)){
			scheduleConfig.cancelTask(taskId);
		}
		TaskInfo taskInfo = this.getTaskById(taskId);
		if(taskInfo != null){
			this.deleteTask(taskId);
		}
	}
	
	/**重置一个任务到队列
	 * @param taskId
	 * @throws NoSuchFieldException 
	 */
	public void reset(long taskId) throws NoSuchFieldException{
		logger.info("the Task[{}] reset start.", taskId);
		TaskInfo taskInfo = this.getTaskById(taskId);
		if(taskInfo != null && checkParam(taskInfo)){
			Task task = taskFactory(taskInfo);	
			scheduleConfig.resetTask(taskId, task);
			//立即启动执行
			if("Y".equalsIgnoreCase(taskInfo.getIsImmediExe())){
				this.newThreadTaskStart(taskInfo);
				}
		}else{
			logger.error("the Task[{}] in DB not exist or param lost.", taskId);
		}		
	}
	
	/**更新一个任务到队列,并更新任务表
	 * @param taskInfo
	 * @throws NoSuchFieldException 
	 */
	public void update(TaskInfo taskInfo) throws NoSuchFieldException{
		logger.info("the Task[{}] update start.", taskInfo.getTaskId());
		if(taskInfo != null && taskInfo.getTaskId()> 0){
			this.updateTask(taskInfo);
			TaskInfo newTask = this.getTaskById(taskInfo.getTaskId());
			Task task = taskFactory(newTask);	
			scheduleConfig.resetTask(newTask.getTaskId(), task);
			//立即启动执行
			if("Y".equalsIgnoreCase(taskInfo.getIsImmediExe())){
				this.newThreadTaskStart(newTask);
			 }
		}else{
			logger.error("the Task[{}] param lost.", taskInfo.getTaskId());
		}		
	}
	
	/**手动立即执行一个任务
	 * @param taskId
	 */
	public void startUp(long taskId){
		logger.info("the Task[{}] startUp start.", taskId);
		TaskInfo taskInfo = this.getTaskById(taskId);
		if(taskInfo != null && checkParam(taskInfo)){
			newThreadTaskStart(taskInfo);
		}else{
			logger.error("the Task[{}] in DB not exist or param lost.", taskId);
		}
	}
	
	/**新线程启动task
	 * 
	 * @param taskInfo
	 */
	private void newThreadTaskStart(TaskInfo taskInfo){
		new Thread(scheduleService.getTaskRunnable(taskInfo.getUrl(), 
				taskInfo.getMethod(), taskInfo.getPostParam())).start();
	}
	
	/**CronTask构造
	 * @param taskInfo
	 * @return
	 */
	private Task taskFactory(TaskInfo taskInfo){
		return new CronTask(scheduleService.getTaskRunnable(taskInfo.getUrl(), 
				taskInfo.getMethod(), taskInfo.getPostParam()), taskInfo.getCron());
	}
	
	/**参数校验
	 * @param taskInfo
	 * @return
	 */
	private boolean checkParam(TaskInfo taskInfo){
		
		if(StringUtils.isEmpty(taskInfo.getUrl()) || StringUtils.isEmpty(taskInfo.getMethod())
				|| StringUtils.isEmpty(taskInfo.getCron())){
			logger.error("checkParam taskInfo not pass! url: {}, method: {}, cron: {}",
					taskInfo.getUrl(), taskInfo.getMethod(), taskInfo.getCron());
			return false;
		}
		return true;
	}		
	
	
	/**查询任务列表
	 * @param task
	 * @return
	 */
	public List<TaskInfo> getTasks(TaskInfo task){	
		logger.info("getTasks param: {}", task);
		task.setState("1");
		return taskMapper.getTasks(task);		
	}
	

	/**查询任务列表数量
	 * @param task
	 * @return
	 */
	public int getTasksCnt(TaskInfo task){	
		logger.info("getTasksCnt param: {}", task);
		task.setState("1");
		return taskMapper.getTasksCnt(task);		
	}
	
	/**查询任务ById
	 * @param task
	 * @return
	 */
	public TaskInfo getTaskById(long taskId){	
		logger.info("getTaskById param: {}", taskId);
		return taskMapper.getTaskById(taskId);		
	}
	
	/**查询taskId
	 * @param taskInfo
	 * @return
	 */
	public long getTaskId(TaskInfo task) {
		logger.info("getTaskId param: {}", task);
		task.setState("1");
		return taskMapper.getTaskId(task);	
	}
	
	/**查询cronTask列表
	 * @return
	 */
	public List<TaskInfo> getCronTasks(){
		TaskInfo task = new TaskInfo();
		task.setTaskType(TaskTypeEnum.CRON_TASK.getType());
		this.getTasks(task);
		return getTasks(task);
	}
	
	/**增加定时任务
	 * @param task
	 * @return
	 */
	public int addTask(TaskInfo task){
		logger.info("addTask param: ", task);
		task.setState("1");
		task.setCreateUid("SYSTEM");
		task.setLastModifyUid("SYSTEM");
		task.setCreateTime(System.currentTimeMillis());
		task.setLastModifyTime(System.currentTimeMillis());
		return taskMapper.addTask(task);
	}
	
	/**更新定时任务
	 * @param task
	 * @return
	 */
	public int updateTask(TaskInfo task){
		logger.info("updateTask param: ", task);
		return taskMapper.updateTask(task);
	}
	
	/**删除定时任务
	 * @param task
	 * @return
	 */
	public int deleteTask(long taskId){
		logger.info("deleteTask param: ", taskId);
		return taskMapper.deleteTask(taskId);
	}
	
	/**查询fixedRateTask列表
	 * @return
	 */
	public List<TaskInfo> getFixedRateTasks(){
		//TODO
		return null;
	}
	
	/**查询fixedDelayTask列表
	 * @return
	 */
	public List<TaskInfo> getFixedDelayTasks(){
		//TODO
		return null;
	}
	
	/**查询tiggerTask列表
	 * @return
	 */
	public List<TaskInfo> getTriggerTasks(){
		//TODO
		return null;
	}

}
