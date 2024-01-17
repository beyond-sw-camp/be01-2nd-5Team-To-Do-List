package com.example.team5_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.team5_project.model.Project;
import com.example.team5_project.model.User;

public interface ProjectRepository extends JpaRepository<Project, Long> {


}
