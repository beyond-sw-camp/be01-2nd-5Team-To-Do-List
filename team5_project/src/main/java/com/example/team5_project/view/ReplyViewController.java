package com.example.team5_project.view;

import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.team5_project.Service.ReplyService;
import com.example.team5_project.Service.ToDoListService;
import com.example.team5_project.Service.UserService;
import com.example.team5_project.model.Reply;
import com.example.team5_project.model.ReplyDTO;
import com.example.team5_project.model.ToDoList;
import com.example.team5_project.model.ToDoListDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReplyViewController {
	private final UserService userService;
	private final ReplyService replyService;
	
	@PostMapping("/{listId}/reply/create")
	public String createReply(@PathVariable("listId") Long listId, ReplyDTO replyDTO) {
		
		replyDTO.setListId(listId);
		replyService.createReply(replyDTO, userService.getMyUserWithAuthorities());

		return "redirect:/" + listId;		
	}
	
	@PostMapping("/{listId}/reply/{replyId}/update")
	public String updateReply(@PathVariable("listId") Long listId, @PathVariable("replyId") Long replyId, ReplyDTO replyDTO) {
		replyDTO.setListId(listId);
		replyService.updateReply(replyId, replyDTO, userService.getMyUserWithAuthorities());

		return "redirect:/" + listId;
	}
	
	@PostMapping("/{listId}/reply/{replyId}/delete")
	public String deleteReply(@PathVariable("listId") Long listId, @PathVariable("replyId") Long replyId) {
		replyService.deleteReply(replyId, userService.getMyUserWithAuthorities());

		return "redirect:/" + listId;		
	}
}
