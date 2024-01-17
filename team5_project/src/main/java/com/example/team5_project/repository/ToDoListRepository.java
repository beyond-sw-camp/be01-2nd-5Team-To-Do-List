package com.example.team5_project.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.team5_project.model.Project;
import com.example.team5_project.model.ToDoList;
import com.example.team5_project.model.User;

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {

	List<ToDoList> findByListDescContaining(String keyword);

	Set<ToDoList> findAllById(Long userId);

	Set<ToDoList> findAllByUser(User user);

	Set<ToDoList> findByParentId(Long parentId);

	Set<ToDoList> findAllByProject(Project project);
	
	List<ToDoList> findAllByProjectAndEndDateAfter(Project project, Date tomorrowMidnight);

	List<ToDoList> findAllByProjectAndEndDateBefore(Project project, Date tomorrowMidnight);

	Set<ToDoList> findByProjectAndListNameContaining(Project project, String keyword);

	Set<ToDoList> findByProjectAndListDescContaining(Project project, String keyword);

}
