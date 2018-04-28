package com.doowhop.schedule.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.doowhop.schedule.domain.TaskInfo;

@Mapper
public interface TaskMapper {
	
	List<TaskInfo> getTasks(TaskInfo task);
	
	int addTask(TaskInfo task);
	
	int updateTask(TaskInfo task);
	
	int deleteTask(long taskId);
	
	int getTasksCnt(TaskInfo task);

	TaskInfo getTaskById(long taskId);

	long getTaskId(TaskInfo taskInfo);

}
