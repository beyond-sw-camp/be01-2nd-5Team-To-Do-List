package com.example.team5_project.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team5_project.model.ERole;
import com.example.team5_project.model.Project;
import com.example.team5_project.model.ProjectUser;
import com.example.team5_project.model.Role;
import com.example.team5_project.model.User;
import com.example.team5_project.model.UserDto;
import com.example.team5_project.repository.ProjectRepository;
import com.example.team5_project.repository.ProjectUserRepository;
import com.example.team5_project.repository.RoleRepository;
import com.example.team5_project.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProjectRepository projectRepository;
    private final ProjectUserRepository projectUserRepository;
    
    @Transactional
    public User signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        
        if (userRepository.findOneWithAuthoritiesByEmail(userDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 이메일입니다.");
        }

        // 가입되어 있지 않은 회원이면,
        // 권한 정보 만들고
        Role authority = Role.builder()
                .name(ERole.ROLE_USER)
                .id(roleRepository.findByName(ERole.ROLE_USER).getId())
                .build();

        // 유저 정보를 만들어서 save
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .email(userDto.getEmail())
                .roles(Collections.singleton(authority))
                .build();
        
        Set<User> users = new HashSet<>();
        users.add(user);
        Project project = Project.builder()
        				.projectName("own project")
        				.build();
        
        ProjectUser projectUser = ProjectUser.builder()
        					.user(user)
        					.project(project)
        					.isJoin(true)
        					.build();
        
        System.out.println("1");
        projectRepository.save(project);
        System.out.println("2");
        projectUserRepository.save(projectUser);
        System.out.println("3");

        return userRepository.save(user);
    }

    // 유저,권한 정보를 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    // 현재 securityContext에 저장된 username의 정보만 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }
    
    @Transactional
    public User updateUser(UserDto userDto, User user) {
    	user.setEmail(userDto.getEmail());
    	user.setNickname(userDto.getNickname());
    	user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }
    
    @Transactional
    public void deleteUser(User user) {
    	userRepository.delete(user);
    }
    
}
