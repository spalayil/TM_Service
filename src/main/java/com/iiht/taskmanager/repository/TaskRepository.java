package com.iiht.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iiht.taskmanager.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	public Task findBytaskId(long id);
 
}
