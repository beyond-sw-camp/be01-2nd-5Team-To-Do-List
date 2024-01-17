package com.example.team5_project.view;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.team5_project.Service.ProjectService;
import com.example.team5_project.Service.ToDoListService;
import com.example.team5_project.Service.UserService;
import com.example.team5_project.model.Project;
import com.example.team5_project.model.ProjectDTO;
import com.example.team5_project.model.ProjectUser;
import com.example.team5_project.model.ToDoList;
import com.example.team5_project.model.ToDoListDTO;
import com.example.team5_project.model.User;
import com.example.team5_project.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class ProjectViewController {
	private final UserService userService;
	private final ToDoListService toDoListService;
	private final ProjectService projectService;
	private final UserRepository userRepository;

	
//	private final LikeService likeService;
	
	@GetMapping("/project/create")
	public String createProject(Model model, ProjectDTO projectDTO) {
		model.addAttribute("projectDTO", projectDTO);
		return "project_form";		
	}
	
	@GetMapping("/project/list")
	public String projectList(Model model) {
		Optional<User> user = userService.getMyUserWithAuthorities();
		Set<ProjectUser> projectUsers = projectService.readJoinProjectUser(user);
		model.addAttribute("projectUsers", projectUsers);
		return "project_list";		
	}
	
	@PostMapping("/project/create")
	public String createProject(ProjectDTO projectDTO) {
		Optional<User> user = userService.getMyUserWithAuthorities();
		
		Project project = projectService.createProject(projectDTO, user);
		Set<User> users = userRepository.findAllByEmailIn(projectDTO.getUserEmail());
		System.out.println(projectDTO.getUserEmail());
		System.out.println(users);
		for(User item : users) {
			projectService.addProjectUser(project, item);
		}
		return "redirect:/project/" + project.getId();		
	}
	
	@GetMapping("/project/{projectId}")
	public String createProject(Model model, @PathVariable("projectId") Long projectId) {
		
		Optional<User> user = userService.getMyUserWithAuthorities();
		Project project = projectService.readProject(projectId, user);
		Set<ToDoList> lists = toDoListService.readList(project);
				
        model.addAttribute("listDTO", new ToDoListDTO());
        model.addAttribute("lists", lists);        
        model.addAttribute("project", project);
		return "list";	
	}
	
	@PostMapping("/user/invite/accept")
	public String acceptProject(@RequestParam("projectId") Long projectUserid) {
		Optional<User> user = userService.getMyUserWithAuthorities();
		
		ProjectUser projectUser = projectService.acceptProjectUser(projectUserid);
		
		return "redirect:/project/" + projectUser.getProject().getId();		
	}
	
	@PostMapping("/user/invite/reject")
	public String rejectProject(@RequestParam("projectId") Long projectUserid) {
		Optional<User> user = userService.getMyUserWithAuthorities();
		
		projectService.rejectProjectUser(projectUserid);
		
		return "redirect:/user";		
	}
}
