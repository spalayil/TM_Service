package com.cognizant.tm;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cognizant.tm.model.Task;
import com.cognizant.tm.repository.TaskRepository;
import com.cognizant.tm.service.TaskServiceImpl;

public class UTService {

	@InjectMocks
	TaskServiceImpl tservice;

	@Mock
	TaskRepository repos;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getAllTaskTest() throws ParseException {
		SimpleDateFormat dformat = new SimpleDateFormat("MM-dd-yyyy");
		Date sdate1 = dformat.parse("12-01-2018");
		Date edate1 = dformat.parse("12-01-2018");
		Date sdate2 = dformat.parse("12-01-2018");
		Date edate2 = dformat.parse("12-01-2018");
		Task task1 = new Task();
		Task task2 = new Task();
		task1.setTaskId(1);
		task1.setTaskName("Task1");
		task1.setPriority(12);
		task1.setParentName("parentask1");
		task1.setStartDate(edate1);
		task1.setEndDate(sdate1);
		task1.setStatus(true);
		task2.setTaskId(2);
		task2.setTaskName("Task2");
		task2.setPriority(13);
		task2.setParentName("parentask2");
		task2.setStartDate(edate2);
		task2.setEndDate(sdate2);
		task1.setStatus(true);

		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task1);
		tasks.add(task2);
		when(repos.findAll()).thenReturn(tasks);
		// test
		List<Task> taskList = tservice.getTasks();
		assertEquals(2, taskList.size());
		verify(repos, times(1)).findAll();

	}

	@Test
	public void createTaskTest() throws ParseException {
		SimpleDateFormat dformat = new SimpleDateFormat("MM-dd-yyyy");
		Date sdate = dformat.parse("12-01-2018");
		Date edate = dformat.parse("12-01-2018");
		Task object = new Task();
		object.setTaskId(4);
		object.setTaskName("Task1");
		object.setPriority(12);
		object.setParentName("parentask1");
		object.setStartDate(edate);
		object.setEndDate(sdate);
		object.setStatus(true);
		tservice.addTask(object);

		verify(repos, times(1)).save(object);
	}

	@Test
	public void updateTaskTest() throws ParseException {
		SimpleDateFormat dformat = new SimpleDateFormat("MM-dd-yyyy");
		Date sdate = dformat.parse("12-01-2018");
		Date edate = dformat.parse("12-01-2018");
		Task object = new Task();
		object.setTaskId(4);
		object.setTaskName("Task1");
		object.setPriority(12);
		object.setParentName("parentask1");
		object.setStartDate(edate);
		object.setEndDate(sdate);
		object.setStatus(true);
		tservice.updateTask(object);

		verify(repos, times(1)).save(object);
	}

	@Test
	public void getTaskByIdTest() throws ParseException {
		SimpleDateFormat dformat = new SimpleDateFormat("MM-dd-yyyy");
		Date sdate = dformat.parse("12-01-2018");
		Date edate = dformat.parse("12-01-2018");
		Task object = new Task();
		object.setTaskId(4);
		object.setTaskName("Task1");
		object.setPriority(12);
		object.setParentName("parentask1");
		object.setStartDate(edate);
		object.setEndDate(sdate);
		object.setStatus(true);
		when(repos.findBytaskId((long) 4)).thenReturn(object);
		assertEquals(4, object.getTaskId());
		assertEquals("Task1", object.getTaskName());
		assertEquals(12, object.getPriority());
		assertEquals("parentask1", object.getParentName());
		assertEquals(sdate, object.getStartDate());
		assertEquals(edate, object.getEndDate());
		assertEquals(true, object.isStatus());
	}

}
