package com.doowhop.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.doowhop.schedule.domain.ScheduleTask;

@Component
public class TaskRunnable {
	
	@Autowired
	RequestSender requestSender;
	
	/**获取调度任务Runnable
	 * @param scheduleTask
	 * @return
	 */
	public Runnable getTaskRunnable(final ScheduleTask scheduleTask){		
		return new Runnable() {			
			@Override
			public void run() {
				requestSender.sendRequest(scheduleTask);		
			}
		};		
	}
	
	
	/**新线程启动task
	 * 
	 * @param taskInfo
	 */
	public void newThreadTaskStart(ScheduleTask scheduleTask){
		new Thread(getTaskRunnable(scheduleTask)).start();
	}

}
