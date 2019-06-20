package com.iiht.taskmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iiht.taskmanager.model.Task;
import com.iiht.taskmanager.service.TaskService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@PostMapping("/task/create")
	public ResponseEntity<Object> addTask(@RequestBody Task task) {
		if(task==null || task.getStartDate()==null || task.getEndDate()==null )
		{
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}else {
		
		taskService.addTask(task);
		     return  new ResponseEntity<Object>(task, HttpStatus.CREATED);
	}

	}

	@GetMapping("/task")
	public List<Task> getTasks() {
		List<Task> task = taskService.getTasks();
		return task;
	}

	@PutMapping("/task/update/{id}")
	public ResponseEntity<Object> updateTask(@PathVariable("id") long id, @RequestBody Task task) {
		Task t = taskService.getTaskById(id);
		t.setTaskId(task.getTaskId());
		t.setTask(task.getTask());
		t.setPriority(task.getPriority());
		t.setParentTask(task.getParentTask());
		t.setStartDate(task.getStartDate());
		t.setEndDate(task.getEndDate());
		taskService.updateTask(t);
		return new ResponseEntity<Object>(task, HttpStatus.OK);

	}
	
	@PutMapping("/task/endtask/{id}")
	public ResponseEntity<Object> endTask(@PathVariable("id") long id) {
		Task tk = taskService.getTaskById(id);		
		tk.setActiveTask(true);
		taskService.updateTask(tk);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	
	@DeleteMapping("/task/deletetask/{id}")
	public ResponseEntity<Object> deleteTask(@PathVariable("id") long id) {
		taskService.deleteTask(id);
		return new ResponseEntity<Object>( HttpStatus.OK);
	}

}
