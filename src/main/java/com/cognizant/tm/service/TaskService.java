package com.cognizant.tm.service;

import java.util.List;

import com.cognizant.tm.model.Task;



public interface TaskService  {
	public Task addTask(Task task);
	public List<Task> getTasks();
	public Task updateTask(Task task);
	public Task getTaskById(long id);
	public void deleteTask(long id);
}
