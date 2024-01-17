package com.example.team5_project.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.team5_project.Service.EmotionService;
import com.example.team5_project.Service.UserService;
import com.example.team5_project.model.EmotionDTO;
import com.example.team5_project.model.EmotionUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmotionController {
	private final UserService userService;
	private final EmotionService emotionService;
		
	@PostMapping("/emotion/check")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<Optional<EmotionUser>> checkEmotion(@RequestBody EmotionDTO emotionDTO) {
				
		return ResponseEntity.ok(emotionService.checkEmotion(emotionDTO, userService.getMyUserWithAuthorities()));
	}
	
	@GetMapping("/emotion/check/{listId}")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<Optional<EmotionUser>> checkEmotion(@PathVariable Long listId) {
				
		return ResponseEntity.ok(emotionService.readEmotion(listId, userService.getMyUserWithAuthorities()));
	}
}
