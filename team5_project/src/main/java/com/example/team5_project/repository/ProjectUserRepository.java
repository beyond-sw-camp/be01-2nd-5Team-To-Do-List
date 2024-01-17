package com.example.team5_project.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.team5_project.model.Project;
import com.example.team5_project.model.ProjectUser;
import com.example.team5_project.model.User;
import com.example.team5_project.repository.mapping.GetProjectMapping;
import com.example.team5_project.repository.mapping.GetUserMapping;


public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long> {

	Set<GetUserMapping> findUserByProject(Project project);
	Set<ProjectUser> findByProject(Project project);
	Set<ProjectUser> findByUser(User user);
//	Project findFirstByUser(User user);
	Set<ProjectUser> findByUserAndIsJoinFalse(User user);
	GetProjectMapping findFirstByUser(User user);
	Set<ProjectUser> findByUserAndIsJoinTrue(User user);


}

