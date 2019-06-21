package com.cognizant.tm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "Tasks")
public class Task implements Comparable<Task> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long taskId;

	@Column(name = "taskName")
	private String taskName;

	@Column(name = "priority")
	private int priority;

	@Column(name = "parentName")
	private String parentName;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "startDate")
	private Date startDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "endDate")
	private Date endDate;

	@Column(name = "status")
	private boolean status;

	

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", taskName=" + taskName + ", priority=" + priority + ", parentName=" + parentName
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + "]";
	}

	@Override
	public int compareTo(Task arg0) {

		return (this.priority < arg0.priority ? -1 : (this.priority > arg0.priority ? 1 : 0));

	}

}
