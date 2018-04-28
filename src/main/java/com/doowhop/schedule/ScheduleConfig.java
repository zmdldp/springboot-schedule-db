package com.doowhop.schedule;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.Task;
import org.springframework.util.CollectionUtils;

import com.doowhop.schedule.service.ScheduleService;
import com.doowhop.schedule.service.TaskService;
import com.doowhop.schedule.util.BeanUtils;

@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ScheduleService scheduleService;

	@Autowired
	TaskService taskService;

	@Value("${schedule.threadpool.size}")
	private int size;
	
	//维护一个taskId=scheduledTask的映射, 方便终止任务
	private Map<Object, ScheduledTask> scheduledTaskMap = new ConcurrentHashMap<Object, ScheduledTask>();
	
	//公用的注册器
	private ScheduledTaskRegistrar taskRegistrar;
	
	//springboot托管的任务列表(可以通过反射获得动态添加删除,或者通过ScheduledTaskRegistrar的addCronTask或setCronTasks方法添加)
	//此工程未使用上述方式,而是直接调用ScheduledTaskRegistrar的scheduleCronTask(CronTask task)方法直接调度
	//private Set<ScheduledTask> scheduledTasks;

	
	/**
	 * 实现ScheduledTaskRegistrar中的configureTasks方法，设置调度器 (non-Javadoc)
	 * 
	 * @see org.springframework.scheduling.annotation.SchedulingConfigurer#configureTasks(org.springframework.scheduling.config.ScheduledTaskRegistrar)
	 */
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		
		// 创建一个线程池调度器
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		// 设置线程池容量
		scheduler.setPoolSize(size);
		// 线程名前缀
		scheduler.setThreadNamePrefix("task-");
		// 等待时常
		scheduler.setAwaitTerminationSeconds(60);
		// 当调度器shutdown被调用时等待当前被调度的任务完成
		scheduler.setWaitForTasksToCompleteOnShutdown(true);
		// 设置当任务被取消的同时从当前调度器移除的策略
		scheduler.setRemoveOnCancelPolicy(true);
		// explicitly call scheduler.initialize() after setting all properties but before returning the scheduler.
		scheduler.initialize();
		//设置任务注册器的调度器
		taskRegistrar.setScheduler(scheduler);
		//初始化公用参数
		this.taskRegistrar = taskRegistrar;
	}

	/**
	 * 添加任务
	 * 
	 * @param taskId
	 * @param task
	 */
	public void addTask(long taskId, Task task) {
		if (!hasTask(taskId)) {
			//手动调度任务执行
			ScheduledTask scheduledTask = taskRegistrar.scheduleCronTask((CronTask) task);
			//放入维护的map
			scheduledTaskMap.put(taskId, scheduledTask);
			logger.info("Add Task[{}] is Success!", taskId);
		}else{
			logger.error("the taskId[{}] has been added.", taskId);
		}		
	}

	/**
	 * 添加任务的另一种执行方法
	 * 
	 * @param taskId
	 * @param task
	 */
	@SuppressWarnings("unused")
	public void addTaskAnotherMethod(long taskId, Task task) throws Exception {
		if (!hasTask(taskId)) {
			TaskScheduler scheduler = taskRegistrar.getScheduler();
			ScheduledFuture<?> future = scheduler.schedule(task.getRunnable(),
					((CronTask) task).getTrigger());
			// ...
		}else{
			logger.error("the taskId[{}] has been added.", taskId);
		}		
	}

	/**
	 * 取消任务
	 * 
	 * @param taskId
	 * @throws NoSuchFieldException
	 */
	public void cancelTask(long taskId) throws NoSuchFieldException {
		if(!CollectionUtils.isEmpty(scheduledTaskMap)){
			ScheduledTask scheduledTask = scheduledTaskMap.get(taskId);
			if (scheduledTask != null) {
				scheduledTask.cancel();
				logger.info("Cancel Task[{}] result: {} !", taskId, 
						((Future<?>) BeanUtils.getProperty(scheduledTask, "future")).isCancelled());
			}else{
				logger.error("the taskId[{}] does not exist.", taskId);
			}
			scheduledTaskMap.remove(taskId);
		}else{
			logger.error("the system does not have any scheduledTask.");
		}
		
	}

	/**
	 * 重置任务
	 * 
	 * @param taskId
	 * @param task
	 * @throws NoSuchFieldException 
	 */
	public void resetTask(long taskId, Task task) throws NoSuchFieldException {
		cancelTask(taskId);
		addTask(taskId, task);
		logger.info("reset Task[{}] success!", taskId);
	}

	/**
	 * 是否存在任务
	 * 
	 * @param taskId
	 * @return
	 */
	public boolean hasTask(long taskId) {
		return this.scheduledTaskMap.containsKey(taskId);
	}
	
	/**
	 * 使用反射获取springboot托管的任务列表
	 * 也可使用taskRegistrar.getCronTaskList()/getFix...分别获取任务列表
	 * 
	 * @return 返回当前的任务列表。
	 */

//	public Set<ScheduledTask> getScheduledTasks() throws NoSuchFieldException {
//		if (CollectionUtils.isEmpty(scheduledTasks)) {
//			try {
//				scheduledTasks = (Set<ScheduledTask>) BeanUtils.getProperty(
//						taskRegistrar, "scheduledTasks");
//			} catch (NoSuchFieldException e) {
//				throw new NoSuchFieldException(
//						"not found ScheduledTask field.");
//			}
//		}
//		return scheduledTasks;
//	}
	
	/**获取已启动的任务列表
	 * @return
	 */
	public Set<Object> getRunningTaskIds(){
		return scheduledTaskMap.keySet();
	}

}