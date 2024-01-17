package com.example.team5_project.Service;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team5_project.model.Emotion;
import com.example.team5_project.model.EmotionDTO;
import com.example.team5_project.model.EmotionReply;
import com.example.team5_project.model.EmotionUser;
import com.example.team5_project.model.Reply;
import com.example.team5_project.model.ToDoList;
import com.example.team5_project.model.User;
import com.example.team5_project.repository.EmotionReplyRepository;
import com.example.team5_project.repository.EmotionRepository;
import com.example.team5_project.repository.EmotionUserRepository;
import com.example.team5_project.repository.ReplyRepository;
import com.example.team5_project.repository.ToDoListRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmotionService {

	private final EmotionRepository emotionRepository;
	private final EmotionUserRepository emotionUserRepository;
	private final EmotionReplyRepository emotionReplyRepository;
	private final ToDoListRepository toDoListRepository;
	private final ReplyRepository replyRepository;

	@Transactional
	public Optional<EmotionUser> checkEmotion(EmotionDTO emotionDTO, Optional<User> user) {

		Emotion emotion = emotionRepository.findByName(emotionDTO.getName());
		if (emotion == null) {
			throw new RuntimeException("없는 감정표현입니다.");
		}
		
		
		Optional<ToDoList> todoList = toDoListRepository.findById(emotionDTO.getListId());

		EmotionUser emotionUser = EmotionUser.builder()
				.user(user.get())
				.list(todoList.get())
				.emotion(emotion)
				.build();

	
		Optional<EmotionUser> findEmotionUser = emotionUserRepository.findByListIdAndUserId(emotionDTO.getListId(), user.get().getId());
		
		if(findEmotionUser.isEmpty()) {
			emotionUserRepository.save(emotionUser);			
		} else if(findEmotionUser.get().getEmotion().getId() != emotion.getId()){
			findEmotionUser.get().setEmotion(emotion);
			emotionUserRepository.save(findEmotionUser.get());
		} else {
			emotionUserRepository.delete(findEmotionUser.get());
		}

		Optional<EmotionUser> result = emotionUserRepository.findByListIdAndUserId(emotionDTO.getListId(), user.get().getId());
		return result.isPresent() ? result : null;
	}
	
	@Transactional
	public Optional<EmotionUser> readEmotion(Long listId, Optional<User> user){

		Optional<EmotionUser> result = emotionUserRepository.findByListId(listId);
		return result;
	}
	
	@Transactional
	public Set<EmotionUser> readAllEmotion(Long listId, Optional<User> user){

		Set<EmotionUser> result = emotionUserRepository.findAllByListId(listId);
		return result;
	}
	
	@Transactional
	public Optional<EmotionReply> checkReplyEmotion(Long replyId, EmotionDTO emotionDTO, Optional<User> user) {

		Emotion emotion = emotionRepository.findByName(emotionDTO.getName());
		if (emotion == null) {
			throw new RuntimeException("없는 감정표현입니다.");
		}
		
		Optional<Reply> reply = replyRepository.findById(replyId);

		EmotionReply emotionUser = EmotionReply.builder()
									.user(user.get())
									.reply(reply.get())
									.emotion(emotion)
									.list(reply.get().getList())
									.build();

	
		Optional<EmotionReply> findEmotionUser = emotionReplyRepository.findByReplyIdAndUserId(replyId, user.get().getId());
		
		if(findEmotionUser.isEmpty()) {
			emotionReplyRepository.save(emotionUser);			
		} else if(findEmotionUser.get().getEmotion().getId() != emotion.getId()){
			findEmotionUser.get().setEmotion(emotion);
			emotionReplyRepository.save(findEmotionUser.get());
		} else {
			emotionReplyRepository.delete(findEmotionUser.get());
		}

		Optional<EmotionReply> result = emotionReplyRepository.findByReplyIdAndUserId(replyId, user.get().getId());
		return result.isPresent() ? result : null;
	}
	
	@Transactional
	public Optional<EmotionReply> readReplyEmotion(Long replyId, Optional<User> user){
		
		Optional<EmotionReply> result = emotionReplyRepository.findByReplyIdAndUserId(replyId, user.get().getId());
		
		return result.isPresent() ? result : null;
	}
	
	@Transactional
	public Set<EmotionReply> readReplyAllEmotion(Long replyId, Optional<User> user){
		
		Set<EmotionReply> result = emotionReplyRepository.findAllByReplyId(replyId);
		
		return result;
	}

}
