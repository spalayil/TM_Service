package com.cognizant.tm.controller;

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

import com.cognizant.tm.model.Task;
import com.cognizant.tm.service.TaskService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class TmController {

	@Autowired
	private TaskService taskService;

	@PostMapping("/task/addTask")
	public ResponseEntity<Object> addTask(@RequestBody Task task) {
		System.out.println("************123"+task.toString());
		if (task == null || task.getStartDate() == null || task.getEndDate() == null) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		} else {
			System.out.println("************"+task.toString());
			taskService.addTask(task);
			return new ResponseEntity<Object>(task, HttpStatus.CREATED);
		}

	}

	@DeleteMapping("/task/deletetask/{id}")
	public ResponseEntity<Object> deleteTask(@PathVariable("id") long id) {
		taskService.deleteTask(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
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
		t.setTaskName(task.getTaskName());
		t.setPriority(task.getPriority());
		t.setParentName(task.getParentName());
		t.setStartDate(task.getStartDate());
		t.setEndDate(task.getEndDate());
		taskService.updateTask(t);
		return new ResponseEntity<Object>(task, HttpStatus.OK);

	}

	@PutMapping("/task/endtask/{id}")
	public ResponseEntity<Object> endTask(@PathVariable("id") long id) {
		System.out.println("************id"+id);
		Task tk = taskService.getTaskById(id);
		System.out.println("************123"+tk.toString());
		tk.setStatus(false);
		taskService.updateTask(tk);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}
