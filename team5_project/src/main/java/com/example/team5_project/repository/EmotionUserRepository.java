package com.example.team5_project.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.team5_project.model.EmotionUser;

public interface EmotionUserRepository extends JpaRepository<EmotionUser, Long> {

	Optional<EmotionUser> findByListIdAndUserId(Long listId, Long id);

	Optional<EmotionUser> findByListId(Long listId);

	Set<EmotionUser> findAllByListId(Long listId);

}
