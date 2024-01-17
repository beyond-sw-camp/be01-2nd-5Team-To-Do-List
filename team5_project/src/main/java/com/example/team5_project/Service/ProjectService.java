package com.example.team5_project.Service;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team5_project.model.Project;
import com.example.team5_project.model.ProjectDTO;
import com.example.team5_project.model.ProjectUser;
import com.example.team5_project.model.User;
import com.example.team5_project.repository.ProjectRepository;
import com.example.team5_project.repository.ProjectUserRepository;
import com.example.team5_project.repository.UserRepository;
import com.example.team5_project.repository.mapping.GetProjectMapping;
import com.example.team5_project.repository.mapping.GetUserMapping;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
	
	private final ProjectRepository projectRepository;
	private final UserRepository userRepository;
	private final ProjectUserRepository projectUserRepository;
	
    @Transactional    
    public Project createProject(ProjectDTO projectDTO, Optional<User> user) {

    	Project project = Project.builder()
    						.projectName(projectDTO.getProjectName())
    						.build();
    	

    	
    	ProjectUser projectUser = ProjectUser.builder()
    								.user(user.get())
    								.project(project)
    								.isJoin(true)
    								.build();

    	projectRepository.save(project);
    	projectUserRepository.save(projectUser);
    	return project;
    }
    
    @Transactional
    public void addProjectUser(Project project, User user) {
    	ProjectUser projectUser = ProjectUser.builder()
									.user(user)
									.project(project)
									.isJoin(false)
									.build();
    	projectUserRepository.save(projectUser);    	
    }
    
    @Transactional
    public void deleteProject(Long projectId, Optional<User> user) {
    	
    	Optional<Project> project = projectRepository.findById(projectId);
    	
    	if(project.isEmpty()) {
    		throw new RuntimeException("해당 프로젝트를 찾을 수 없습니다.");
    	}
    	projectRepository.deleteById(projectId);
    }
    
    @Transactional
    public Project updateProject(Long projectId, ProjectDTO projectDTO, Optional<User> user) {
    	
    	Optional<Project> project = projectRepository.findById(projectId);
    	project.get().setProjectName(projectDTO.getProjectName());
    	
		return projectRepository.save(project.get());
   	
    }
    
    @Transactional
    public Project readProject(Long projectId, Optional<User> user) {
    	Optional<Project> project = projectRepository.findById(projectId);
    	
    	return projectRepository.findById(projectId).get();
    }
    
    
    @Transactional
    public Set<ProjectUser> readProjectUser(Optional<User> user) {
    	
    	Set<ProjectUser> projectUsers = projectUserRepository.findByUserAndIsJoinFalse(user.get());
    	
    	return projectUsers;
    }
    
    @Transactional
    public Set<ProjectUser> readJoinProjectUser(Optional<User> user) {
    	
    	Set<ProjectUser> projectUsers = projectUserRepository.findByUserAndIsJoinTrue(user.get());
    	
    	return projectUsers;
    }
    
    @Transactional
    public ProjectUser acceptProjectUser(Long projectUserId) {
    	ProjectUser projectUser = projectUserRepository.findById(projectUserId).get();
    	projectUser.setIsJoin(true);
    	projectUserRepository.save(projectUser);
    	return projectUser;
    }
    
    @Transactional
    public void rejectProjectUser(Long projectUserId) {
    	projectUserRepository.deleteById(projectUserId);
    }


}
