package com.doowhop.schedule.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.doowhop.schedule.domain.ScheduleTask;
import com.doowhop.schedule.mapper.TaskMapper;
import com.doowhop.schedule.task.ScheduleConfig;
import com.doowhop.schedule.task.TaskRunnable;

/**
 * 定时任务的动态管理
 * 定时任务表的增删改查
 * @author liudp
 *
 */
@Service("taskService")
public class ScheduleTaskService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TaskMapper taskMapper;
	
	@Autowired
	private ScheduleConfig scheduleConfig;
	
	@Autowired
	TaskRunnable taskRunnable;
	
	
	/**
	 * 初始化任务列表, 将数据库中任务添加到定时执行队列
	 */
	public void initTask(){
		logger.info("initTask 初始化...");
		ScheduleTask task = new ScheduleTask();
		task.setStatus("1");//正常状态
		List<ScheduleTask> taskList = this.queryTasks(task);
		if(!CollectionUtils.isEmpty(taskList)){
			for (ScheduleTask scheduleTask : taskList) {		
				scheduleConfig.scheduleTask(scheduleTask);							
			}	
		}
	}
	
	/**
	 * 动态填加任务到执行队列,同时保存调度任务表
	 * @param task
	 */
	public boolean addTask(ScheduleTask task){	
		boolean ret = true;
		logger.info("ScheduleTaskService addTask param: {}", JSON.toJSONString(task));
		int taskCnt = this.queryTasksCnt(task);
		if(taskCnt == 0){
			int addCnt = this.insertTask(task);
			if(addCnt == 1){	
				ret = scheduleConfig.scheduleTask(task);
			}else{
				ret = false;
				logger.error("ScheduleTaskService addTask insert to DB failed.");
			}				
		}else{
			ret = false;
			logger.error("ScheduleTaskService addTask in DB has exist.");
		}
		logger.info("ScheduleTaskService addTask result: {}", ret);
		return ret;
	}
	
	
	/**暂停任务执行队列
	 * @param taskId
	 * @throws  
	 */
	public boolean stopTask(long taskId){
		logger.info("ScheduleTaskService stopTask param is:{}", taskId);
		ScheduleTask task = new ScheduleTask();
		task.setTaskId(taskId);
		task.setStatus("0");//调度状态 0-移除(暂停) 1-正常
		task.setProcState("00");//调度执行状态 00-执行完成(等待执行) 01-执行中
		this.updateTaskById(task);
		return scheduleConfig.cancelTask(taskId);
		
		
	}
	
	
	
	/**启动一个被暂停的任务
	 * @param taskId
	 */
	public boolean startTask(long taskId){
		boolean ret = true;
		logger.info("ScheduleTaskService startTask param:{}.", taskId);
		ScheduleTask task = this.queryTaskById(taskId);
		if(task != null){
			ret = scheduleConfig.scheduleTask(task);
			ScheduleTask updTask = new ScheduleTask();
			updTask.setTaskId(taskId);
			updTask.setStatus("1");////调度状态 0-移除(暂停) 1-正常
			this.updateTaskById(updTask);
		}else{
			ret = false;
			logger.error("ScheduleTaskService startTask Task[{}] in DB not exist.", taskId);
		}			
		return ret;
	}
	
	/**更新一个任务到队列,并更新任务表
	 * @param task 
	 */
	public boolean modifyTask(ScheduleTask task) {
		boolean ret = true;
		logger.info("ScheduleTaskService modifyTask param:{}.", JSON.toJSONString(task));
		ret = scheduleConfig.cancelTask(task.getTaskId());
		this.updateTaskById(task);
		if(ret) {
			ScheduleTask newTask = this.queryTaskById(task.getTaskId());
			ret = scheduleConfig.scheduleTask(newTask);
		}
		return ret;		
	}
	
	/**手动立即执行一个任务
	 * @param taskId
	 */
	public void manualExcuteTask(long taskId){
		logger.info("ScheduleTaskService manualStart param:{}.", taskId);
		ScheduleTask task = this.queryTaskById(taskId);
		if(task != null){
			taskRunnable.newThreadTaskStart(task);
		}else{
			logger.error("ScheduleTaskService not exist!.", taskId);
		}
	}
		

	//mapper处理---这是分割线----//
		
	/**查询任务列表
	 * @param task
	 * @return
	 */
	public List<ScheduleTask> queryTasks(ScheduleTask task){	
		logger.info("ScheduleTaskService selectTasks param: {}", JSON.toJSONString(task));
		List<ScheduleTask> taskList = taskMapper.selectTasks(task);
		logger.info("ScheduleTaskService selectTasks result: {}", JSON.toJSONString(taskList));
		return taskList;
	}
	

	/**查询任务列表数量
	 * @param task
	 * @return
	 */
	public int queryTasksCnt(ScheduleTask task){	
		logger.info("getTasksCnt param: {}", task);
		return taskMapper.selectTasksCnt(task);		
	}
	
	/**查询任务ById
	 * @param task
	 * @return
	 */
	public ScheduleTask queryTaskById(long taskId){	
		logger.info("getTaskById param: {}", taskId);
		return taskMapper.selectTaskById(taskId);		
	}
	
	/**增加定时任务
	 * @param task
	 * @return
	 */
	public int insertTask(ScheduleTask task){
		logger.info("addTask param: {} ", JSON.toJSONString(task));
		task.setStatus("1");//调度状态 0-移除(暂停) 1-正常
		task.setProcState("00");//调度执行状态 00-执行完成(等待执行) 01-执行中
		task.setCreateUid("SYSTEM");
		task.setLastModifyUid("SYSTEM");
		return taskMapper.insertTask(task);
	}
	
	/**更新定时任务
	 * @param task
	 * @return
	 */
	public int updateTaskById(ScheduleTask task){
		logger.info("updateTask param: {}", JSON.toJSONString(task));
		return taskMapper.updateTaskById(task);
	}

}
