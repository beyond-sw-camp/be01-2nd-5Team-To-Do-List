package com.example.team5_project.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.team5_project.Service.LikeService;
import com.example.team5_project.Service.ReplyService;
import com.example.team5_project.Service.UserService;
import com.example.team5_project.model.ReplyDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LikeViewController {
	private final UserService userService;
	private final ReplyService replyService;
	private final LikeService likeService;
	
	@PostMapping("/{listId}/like")
	public String checkLikeList(@PathVariable("listId") Long listId) {
	
		likeService.checkLike(listId, userService.getMyUserWithAuthorities());
		
		return "redirect:/" + listId;		
	}
	
//	@PostMapping("/{listId}/like/{subListId}")
//	public String checkLikeSubList(@PathVariable("listId") Long listId, @PathVariable("subListId") Long subListId) {
//	
//		likeService.checkLike(subListId, userService.getMyUserWithAuthorities());
//		
//		return "redirect:/" + listId;		
//	}
	
//	@PostMapping("/like/reply/{replyId}")
//	public String checkLikeReply(@PathVariable("replyId") Long replyId) {
//	
//		likeService.checkReplyLike(replyId, userService.getMyUserWithAuthorities());
//		
//		return "redirect:/";		
//	}
	
	@PostMapping("/{listId}/like/reply/{replyId}")
	public String checkLikeSubReply(@PathVariable("listId") Long listId, @PathVariable("replyId") Long replyId) {
	
		likeService.checkReplyLike(replyId, userService.getMyUserWithAuthorities());
		
		return "redirect:/" + listId;		
	}
}
