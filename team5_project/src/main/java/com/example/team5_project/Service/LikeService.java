package com.example.team5_project.Service;

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
public class LikeService {

	private final ReplyRepository replyRepository;
	private final ToDoListRepository toDoListRepository;

	@Transactional
	public ToDoList checkLike(Long listId, Optional<User> user) {

		Optional<ToDoList> todoList = toDoListRepository.findById(listId);


		Set<User> likeUsers = todoList.get().getLikeUsers();
		if (likeUsers.contains(user.get())) {
			likeUsers.remove(user.get());
		} else {
			likeUsers.add(user.get());
		}
		todoList.get().setLikeUsers(likeUsers);

		toDoListRepository.save(todoList.get());

		return toDoListRepository.save(todoList.get());
	}
	
	@Transactional
	public void checkReplyLike(Long replyId, Optional<User> user) {

		Optional<Reply> reply = replyRepository.findById(replyId);
		ToDoList todoList = reply.get().getList();


		Set<User> likeUsers = reply.get().getLikeUsers();
		if (likeUsers.contains(user.get())) {
			likeUsers.remove(user.get());
		} else {
			likeUsers.add(user.get());
		}
		reply.get().setLikeUsers(likeUsers);

		replyRepository.save(reply.get());
	}

	@Transactional
	public Set<User> readLike(Long listId, Optional<User> user) {
		
		
		return toDoListRepository.findById(listId).get().getLikeUsers();
	}
	
	@Transactional
	public Set<User> readReplyLike(Long replyId, Optional<User> user) {
	
		return replyRepository.findById(replyId).get().getLikeUsers();

	}
}
