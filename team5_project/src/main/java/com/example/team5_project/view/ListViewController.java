package com.example.team5_project.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.team5_project.Service.EmotionService;
import com.example.team5_project.Service.ReplyService;
import com.example.team5_project.Service.ToDoListService;
import com.example.team5_project.Service.UserService;
import com.example.team5_project.model.EmotionDTO;
import com.example.team5_project.model.EmotionReply;
import com.example.team5_project.model.Project;
import com.example.team5_project.model.Reply;
import com.example.team5_project.model.ReplyDTO;
import com.example.team5_project.model.ToDoList;
import com.example.team5_project.model.ToDoListDTO;
import com.example.team5_project.model.User;
import com.example.team5_project.repository.EmotionRepository;
import com.example.team5_project.repository.ProjectRepository;
import com.example.team5_project.repository.ProjectUserRepository;
import com.example.team5_project.repository.ToDoListRepository;
import com.example.team5_project.repository.mapping.GetProjectMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ListViewController {
	private final UserService userService;
	private final ToDoListService toDoListService;
	private final ReplyService replyService;
	private final EmotionService emotionService;
	
	private final EmotionRepository emotionRepository;
	private final ToDoListRepository toDoListRepository;
	private final ProjectRepository projectRepository;
	private final ProjectUserRepository projectUserRepository;
	
//	private final LikeService likeService;
	
	@GetMapping("/")
	public String goToLogin() {
		return "login_form";
	}
	@GetMapping("/main")
	public String main(Model model) {
		Optional<User> user = userService.getMyUserWithAuthorities();
		
		GetProjectMapping projectMapping = projectUserRepository.findFirstByUser(user.isPresent() ? user.get() : null);
		Project project = projectMapping.getProject();
		Set<ToDoList> lists = toDoListService.readList(project);

		model.addAttribute("listDTO", new ToDoListDTO());
		
		if(lists != null)
	        model.addAttribute("lists", lists);
		if(project != null)
			model.addAttribute("project", project);

		return "list";		
	}
	
	@GetMapping("/today")
	public String today(Model model) {
		Optional<User> user = userService.getMyUserWithAuthorities();
		
		GetProjectMapping projectMapping = projectUserRepository.findFirstByUser(user.get());
		Project project = projectMapping.getProject();

		List<ToDoList> lists = toDoListService.todayList(project);
				
        model.addAttribute("listDTO", new ToDoListDTO());
        model.addAttribute("lists", lists);        

		return "today";		
	}
	
	@GetMapping("/upcoming")
	public String upcoming(Model model) {
		Optional<User> user = userService.getMyUserWithAuthorities();
		
		GetProjectMapping projectMapping = projectUserRepository.findFirstByUser(user.get());
		Project project = projectMapping.getProject();

		List<ToDoList> lists = toDoListService.upcomingList(project);
				
        model.addAttribute("listDTO", new ToDoListDTO());
        model.addAttribute("lists", lists);        
        

		return "upcoming";		
	}
	
	@PostMapping("/list/create")
	public String createList(Model model, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ToDoListDTO listDTO, @RequestParam("project") Long projectId) {
		
		listDTO.setParentId(0L);
		listDTO.setLevel(0);
		
		toDoListService.createProjectList(listDTO, projectId, userService.getMyUserWithAuthorities());

		return "redirect:/main";		
	}
	
	@PostMapping("/list/update")
	public String updateList(@RequestParam("id") Long listId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ToDoListDTO listDTO) {
		toDoListService.updateList(listId, listDTO, userService.getMyUserWithAuthorities());

		return "redirect:/main";
	}
	
	@PostMapping("/list/delete")
	public String deleteList(@RequestParam("id") Long listId) {
		toDoListService.deleteList(listId, userService.getMyUserWithAuthorities());

		return "redirect:/main";		
	}
	
	@PostMapping("/today/list/create")
	public String createTodayList(Model model, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ToDoListDTO listDTO) {
//	public String createList(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ToDoListDTO listDTO, BindingResult bindingResult) {
		listDTO.setParentId(0L);
		listDTO.setLevel(0);
		toDoListService.createList(listDTO, userService.getMyUserWithAuthorities());

		return "redirect:/today";		
	}
	
	@PostMapping("/today/list/update")
	public String updateTodayList(@RequestParam("id") Long listId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ToDoListDTO listDTO) {
		toDoListService.updateList(listId, listDTO, userService.getMyUserWithAuthorities());

		return "redirect:/today";
	}
	
	@PostMapping("/today/list/delete")
	public String deleteTodayList(@RequestParam("id") Long listId) {
		toDoListService.deleteList(listId, userService.getMyUserWithAuthorities());

		return "redirect:/today";		
	}
	
	@PostMapping("/upcoming/list/create")
	public String createUpcomingList(Model model, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ToDoListDTO listDTO) {
//	public String createList(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ToDoListDTO listDTO, BindingResult bindingResult) {
		listDTO.setParentId(0L);
		listDTO.setLevel(0);
		toDoListService.createList(listDTO, userService.getMyUserWithAuthorities());

		return "redirect:/upcoming";		
	}
	
	@PostMapping("/upcoming/list/update")
	public String updateUpcomingList(@RequestParam("id") Long listId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ToDoListDTO listDTO) {
		toDoListService.updateList(listId, listDTO, userService.getMyUserWithAuthorities());

		return "redirect:/upcoming";
	}
	
	@PostMapping("/upcoming/list/delete")
	public String deleteUpcomingList(@RequestParam("id") Long listId) {
		toDoListService.deleteList(listId, userService.getMyUserWithAuthorities());

		return "redirect:/upcoming";		
	}
	
	
	@PostMapping("/{listId}/list/create")
	public String createList(Model model, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ToDoListDTO listDTO, @PathVariable("listId") Long listId, @RequestParam("project") Long projectId) {
		Integer level = toDoListService.readList(listId, userService.getMyUserWithAuthorities()).getLevel();
		listDTO.setParentId(listId);
		listDTO.setLevel(level + 1);
		toDoListService.createList(listDTO, userService.getMyUserWithAuthorities());

		return "redirect:/main";
	}
	
	
	@GetMapping("/{listId}")
	public String test(Model model, @PathVariable("listId") Long listId) {

		Optional<User> user = userService.getMyUserWithAuthorities();
		Set<ToDoList> lists = toDoListService.readParentList(listId, user);
		
		Set<Reply> replys = replyService.readAllReply(listId, user);
		
		GetProjectMapping projectMapping = projectUserRepository.findFirstByUser(user.get());
		Project project = projectMapping.getProject();
		
        model.addAttribute("listDTO", new ToDoListDTO());
        model.addAttribute("replyDTO", new ReplyDTO());        
        model.addAttribute("emotionDTO", new EmotionDTO());        
        
        model.addAttribute("lists", lists);
        model.addAttribute("replys", replys);
        model.addAttribute("listId", listId);
        model.addAttribute("list", toDoListService.readList(listId, user));

        model.addAttribute("emotionList", emotionRepository.findAll());
        
        model.addAttribute("emotion", emotionService.readAllEmotion(listId, user));        

        model.addAttribute("project", project);
        
        Map<Long, Set<EmotionReply>> emotionReply = new HashMap<>();
        
        for(Reply reply : replys) {
        	emotionReply.put(reply.getId(), emotionService.readReplyAllEmotion(reply.getId(), user));
        }
       
        model.addAttribute("emotionReply", emotionReply);

		return "sublist";
	}
	
	@GetMapping("/list/search/{keyword}")
	public String search(@RequestParam("keyword") String keyword, Model model) {
		
		Optional<User> user = userService.getMyUserWithAuthorities();
		List<Set<ToDoList>> matchingLists = toDoListService.searchLists(keyword, userService.getMyUserWithAuthorities());
		
		model.addAttribute("lists", matchingLists);
		
		if(keyword == null)
			return "search";		
		else
			return "searchResult";
	}
	

	@GetMapping("/list/search")
	public String searchMain(Model model) {
		
		return "search";		
	}

}
