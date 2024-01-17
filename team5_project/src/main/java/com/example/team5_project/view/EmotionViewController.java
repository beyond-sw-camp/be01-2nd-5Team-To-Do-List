package com.example.team5_project.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.team5_project.Service.EmotionService;
import com.example.team5_project.Service.ReplyService;
import com.example.team5_project.Service.UserService;
import com.example.team5_project.model.EmotionDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmotionViewController {
	private final UserService userService;
	private final ReplyService replyService;
	private final EmotionService emotionService;
	
	@PostMapping("/{listId}/emotion")
	public String checkLikeList(@PathVariable("listId") Long listId, EmotionDTO emotionDTO) {
		
		emotionDTO.setListId(listId);
	
		emotionService.checkEmotion(emotionDTO, userService.getMyUserWithAuthorities());
		
		return "redirect:/" + listId;		
	}
	
	@PostMapping("/{listId}/emotion/reply/{replyId}")
	public String checkLikeSubReply(@PathVariable("listId") Long listId, @PathVariable("replyId") Long replyId, EmotionDTO emotionDTO) {
	
		emotionService.checkReplyEmotion(replyId, emotionDTO, userService.getMyUserWithAuthorities());
		
		return "redirect:/" + listId;		
	}
}
