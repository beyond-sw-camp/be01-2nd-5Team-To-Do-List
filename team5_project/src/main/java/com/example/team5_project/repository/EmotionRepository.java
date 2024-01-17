package com.example.team5_project.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.team5_project.model.Emotion;

public interface EmotionRepository extends JpaRepository<Emotion, Long> {

	Emotion findByName(String name);

	Set<Emotion> findAllByName(String name);

}
