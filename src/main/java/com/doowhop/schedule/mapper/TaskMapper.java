package com.doowhop.schedule.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.doowhop.schedule.domain.ScheduleTask;

@Mapper
public interface TaskMapper {
	
	List<ScheduleTask> selectTasks(ScheduleTask task);
	
	int insertTask(ScheduleTask task);
	
	int updateTaskById(ScheduleTask task);
		
	int selectTasksCnt(ScheduleTask task);

	ScheduleTask selectTaskById(long taskId);

}
