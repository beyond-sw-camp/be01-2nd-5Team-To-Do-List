package com.example.team5_project.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.team5_project.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findOneWithAuthoritiesByUsername(String username);

	Optional<User> findOneWithAuthoritiesByEmail(String email);

	Set<User> findAllByEmailIn(Set<String> userEmail);

}
