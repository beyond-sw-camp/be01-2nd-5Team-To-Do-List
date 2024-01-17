package com.example.team5_project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.team5_project.Service.ReplyService;
import com.example.team5_project.Service.UserService;
import com.example.team5_project.model.Reply;
import com.example.team5_project.model.ReplyDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReplyController {
	private final UserService userService;
	private final ReplyService replyService;
		
	@PostMapping("/reply/create")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<Reply> createReply(@RequestBody ReplyDTO replyDTO) {
				
		return ResponseEntity.ok(replyService.createReply(replyDTO, userService.getMyUserWithAuthorities()));
	}
	
	@DeleteMapping("/reply/delete/{replyId}")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<String> deleteReply(@PathVariable Long replyId) {
		replyService.deleteReply(replyId, userService.getMyUserWithAuthorities());
		return ResponseEntity.ok(new String("delete reply"));
	}
	
	@PutMapping("/reply/update/{replyId}")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<Reply> updateReply(@PathVariable Long replyId, @RequestBody ReplyDTO replyDTO) {
				
		return ResponseEntity.ok(replyService.updateReply(replyId, replyDTO, userService.getMyUserWithAuthorities()));
	}
	
	@GetMapping("/reply/read/{replyId}")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<Reply> readReply(@PathVariable Long replyId) {
				
		return ResponseEntity.ok(replyService.readReply(replyId, userService.getMyUserWithAuthorities()));
	}
}
