package com.iiht.taskmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.taskmanager.model.Task;
import com.iiht.taskmanager.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService  {

	@Autowired
    private TaskRepository taskRepository;
	
	public Task addTask(Task task) {
		 return taskRepository.save(task);
	}
	
	public List<Task> getTasks(){
		List<Task> task = taskRepository.findAll();
		return task;
	}
	
	@Override
	public Task updateTask(Task task) {
		return taskRepository.save(task);
	}
	
	public Task getTaskById(long id) {
		
		return  taskRepository.findBytaskId(id);
	}
}
