package com.cognizant.tm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.tm.model.Task;



@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	public Task findBytaskId(long id);
 
}
