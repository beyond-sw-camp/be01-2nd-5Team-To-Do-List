package com.example.team5_project.controller;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.team5_project.Service.LikeService;
import com.example.team5_project.Service.UserService;
import com.example.team5_project.model.ListIdDTO;
import com.example.team5_project.model.ToDoList;
import com.example.team5_project.model.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {
	private final UserService userService;
	private final LikeService likeService;
	
	@PostMapping("/like/check")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<ToDoList> checkLike(@RequestBody ListIdDTO listIdDTO) {
				
		return ResponseEntity.ok(likeService.checkLike(listIdDTO.getListId(), userService.getMyUserWithAuthorities()));
	}
	
	@GetMapping("/like/check/{listId}")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<Set<User>> checkLike(@PathVariable Long listId) {
				
		return ResponseEntity.ok(likeService.readLike(listId, userService.getMyUserWithAuthorities()));
	}

}
