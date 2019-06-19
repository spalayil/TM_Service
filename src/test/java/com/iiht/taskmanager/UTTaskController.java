package com.iiht.taskmanager;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iiht.taskmanager.Exception.NoValuesFoundException;
import com.iiht.taskmanager.controller.TaskController;
import com.iiht.taskmanager.model.Task;
import com.iiht.taskmanager.service.TaskService;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class UTTaskController {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Mock
	private TaskService tservice;

	private MockMvc mockMvc;

	@InjectMocks
	private TaskController taskcontroller;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(taskcontroller).build();
	}

	@Test
	public void testListAllTask() throws Exception, ParseException {
		SimpleDateFormat dformat = new SimpleDateFormat("MM-dd-yyyy");
		Date sdate1 = dformat.parse("12-01-2018");
		Date edate1 = dformat.parse("12-01-2018");
		Date sdate2 = dformat.parse("12-01-2018");
		Date edate2 = dformat.parse("12-01-2018");
		Task task1 = new Task();
		Task task2 = new Task();
		task1.setTaskId(1);
		task1.setTask("Task1");
		task1.setPriority(12);
		task1.setParentTask("parentask1");
		task1.setStartDate(edate1);
		task1.setEndDate(sdate1);
		task1.setActiveTask(true);
		task2.setTaskId(2);
		task2.setTask("Task2");
		task2.setPriority(13);
		task2.setParentTask("parentask2");
		task2.setStartDate(edate2);
		task2.setEndDate(sdate2);
		task2.setActiveTask(false);

		List<Task> types = new ArrayList<Task>();
		types.add(task1);
		types.add(task2);
		when(tservice.getTasks()).thenReturn(types);
		mockMvc.perform(get("/api/task")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$[0].taskId", is(1))).andExpect(jsonPath("$[0].task", is("Task1")))
				.andExpect(jsonPath("$[0].priority", is(12))).andExpect(jsonPath("$[0].parentTask", is("parentask1")))
				.andExpect(jsonPath("$[0].startDate", notNullValue()))
				.andExpect(jsonPath("$[0].endDate", notNullValue())).andExpect(jsonPath("$[0].activeTask", is(true)))
				.andExpect(jsonPath("$[1].taskId", is(2))).andExpect(jsonPath("$[1].task", is("Task2")))
				.andExpect(jsonPath("$[1].priority", is(13))).andExpect(jsonPath("$[1].parentTask", is("parentask2")))
				.andExpect(jsonPath("$[1].activeTask", is(false)))

				.andDo(print());
		verify(tservice, times(1)).getTasks();
		verifyNoMoreInteractions(tservice);
	}

	@Test
	public void testPostTask() throws Exception {
		SimpleDateFormat dformat = new SimpleDateFormat("MM-dd-yyyy");
		Date sdate = dformat.parse("12-01-2018");
		Date edate = dformat.parse("12-01-2018");
		Task object = new Task();
		object.setTaskId(4);
		object.setTask("Task1");
		object.setPriority(12);
		object.setParentTask("parentask1");
		object.setStartDate(edate);
		object.setEndDate(sdate);
		object.setActiveTask(true);
		when(tservice.addTask(object)).thenReturn(object);
		mockMvc.perform(
				post("/api/task/create").contentType(APPLICATION_JSON_UTF8).content(TestUtil.ObjecttoJSON(object)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.taskId", is(4)))
				.andExpect(jsonPath("$.task", is("Task1"))).andExpect(jsonPath("$.priority", is(12)))
				.andExpect(jsonPath("$.parentTask", is("parentask1")))
				.andExpect(jsonPath("$.startDate", notNullValue())).andExpect(jsonPath("$.endDate", notNullValue()))
				.andExpect(jsonPath("$.activeTask", is(true))).andDo(print());
	}

	@Test
	public void testPostTaskExceptin() throws Exception {
		Task object = new Task();
		object.setTaskId(4);
		object.setTask("Task1");
		object.setPriority(12);
		object.setParentTask("parentask1");
		object.setStartDate(null);
		object.setEndDate(null);
		object.setActiveTask(true);
		when(tservice.addTask(object)).thenThrow(new NoValuesFoundException());
		mockMvc.perform(post("/api/task/create").contentType(APPLICATION_JSON_UTF8).content(asJsonString(object)))
				.andExpect(status().isBadRequest()).andDo(print());

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testUpdateTask() throws Exception {
		SimpleDateFormat dformat = new SimpleDateFormat("MM-dd-yyyy");
		Date sdate1 = dformat.parse("12-01-2018");
		Date edate1 = dformat.parse("12-01-2018");

		Task task1 = new Task();
		Task task2 = new Task();
		task1.setTaskId(1);
		task1.setTask("Task1");
		task1.setPriority(12);
		task1.setParentTask("parentask1");
		task1.setStartDate(edate1);
		task1.setEndDate(sdate1);
		task1.setActiveTask(false);
		task2.setTaskId(1);
		task2.setTask("Task1");
		task2.setPriority(13);
		task2.setParentTask("parentask1");
		task2.setStartDate(edate1);
		task2.setEndDate(sdate1);
		task2.setActiveTask(true);
		when(tservice.getTaskById(task1.getTaskId())).thenReturn(task1);
		when(tservice.updateTask(task2)).thenReturn(task2);
		mockMvc.perform(put("/api/task/update/{id}", task1.getTaskId()).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.ObjecttoJSON(task2))).andExpect(status().isOk())
				.andExpect(jsonPath("$.priority", is(13))).andDo(print());
	}

	@Test
	public void testEndTask() throws Exception {
		SimpleDateFormat dformat = new SimpleDateFormat("MM-dd-yyyy");
		Date sdate1 = dformat.parse("12-01-2018");
		Date edate1 = dformat.parse("12-01-2018");

		Task task1 = new Task();
		Task task2 = new Task();
		task1.setTaskId(1);
		task1.setTask("Task1");
		task1.setPriority(12);
		task1.setParentTask("parentask1");
		task1.setStartDate(edate1);
		task1.setEndDate(sdate1);
		task1.setActiveTask(false);
		task2.setTaskId(1);
		task2.setTask("Task1");
		task2.setPriority(12);
		task2.setParentTask("parentask1");
		task2.setStartDate(edate1);
		task2.setEndDate(sdate1);
		task2.setActiveTask(true);
		when(tservice.getTaskById(task1.getTaskId())).thenReturn(task1);
		when(tservice.updateTask(task2)).thenReturn(task2);
		mockMvc.perform(put("/api/task/endtask/{id}", task1.getTaskId()).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.ObjecttoJSON(task2))).andExpect(status().isOk())
				.andExpect(jsonPath("$.activeTask", is(true))).andDo(print());
	}
}
