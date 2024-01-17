package com.example.team5_project.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team5_project.model.Reply;
import com.example.team5_project.model.ReplyDTO;
import com.example.team5_project.model.ToDoList;
import com.example.team5_project.model.User;
import com.example.team5_project.repository.ReplyRepository;
import com.example.team5_project.repository.ToDoListRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {
	
	private final ReplyRepository replyRepository;
	private final ToDoListRepository toDoListRepository;
	
    @Transactional
    public Reply createReply(ReplyDTO replyDTO, Optional<User> user) {
    	
    	Optional<ToDoList> todoList = toDoListRepository.findById(replyDTO.getListId());
		
    	Reply reply = Reply.builder()
				.content(replyDTO.getContent())
				.user(user.get())
				.list(todoList.get())
				.build();
    	
  	
    	return replyRepository.save(reply);
    }
    
    @Transactional
    public void deleteReply(Long replyId, Optional<User> user) {
    	
    	Optional<Reply> reply = replyRepository.findById(replyId);
    	
		if(reply.get().getUser().getId() != user.get().getId()) {
	    		throw new RuntimeException("일치하지 않는 유저입니다.");
		}
		
		replyRepository.deleteById(replyId);
		
    }
    @Transactional
    public Reply updateReply(Long replyId, ReplyDTO replyDTO, Optional<User> user) {
    	Optional<Reply> reply = replyRepository.findById(replyId);
    	
		if(reply.get().getUser().getId() != user.get().getId()) {
	    		throw new RuntimeException("일치하지 않는 유저입니다.");
		}
		
		reply.get().setContent(replyDTO.getContent());
  	
    	return replyRepository.save(reply.get());
    }
    
    @Transactional
    public Reply readReply(Long replyId, Optional<User> user) {
    	Optional<Reply> reply = replyRepository.findById(replyId);
    	
		if(reply.get().getUser().getId() != user.get().getId()) {
    		throw new RuntimeException("일치하지 않는 유저입니다.");
		}
		
		return reply.get();
    }
    
    @Transactional
    public Set<Reply> readAllReply(Long listId, Optional<User> user) {
    	Optional<ToDoList> todoList = toDoListRepository.findById(listId);
    	
    	Set<Reply> replys = replyRepository.findByList(todoList.get());

		return replys;
    }
    
}
