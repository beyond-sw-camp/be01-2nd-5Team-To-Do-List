package com.example.team5_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.team5_project.model.ERole;
import com.example.team5_project.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByName(ERole roleUser);

}
