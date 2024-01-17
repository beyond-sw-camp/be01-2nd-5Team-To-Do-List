package com.example.team5_project.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.team5_project.model.EmotionReply;
import com.example.team5_project.model.EmotionUser;
import com.example.team5_project.model.User;

public interface EmotionReplyRepository extends JpaRepository<EmotionReply, Long> {

	Optional<EmotionReply> findByReplyIdAndUserId(Long replyId, Long id);

//	Optional<EmotionReply> findByReplyId(Long replyId);
//
//	Optional<EmotionReply> findByListId(Long listId);

	Optional<EmotionReply> findByReplyId(Long replyId);

	Set<EmotionReply> findAllByReplyId(Long replyId);

}
