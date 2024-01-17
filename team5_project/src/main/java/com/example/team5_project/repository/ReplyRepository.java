package com.example.team5_project.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.team5_project.model.Reply;
import com.example.team5_project.model.ToDoList;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

	Set<Reply> findByList(ToDoList toDoList);

}
