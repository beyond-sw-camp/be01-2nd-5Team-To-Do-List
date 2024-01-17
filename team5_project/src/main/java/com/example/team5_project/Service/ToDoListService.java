package com.example.team5_project.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team5_project.model.CategoryName;
import com.example.team5_project.model.Project;
import com.example.team5_project.model.ProjectUser;
import com.example.team5_project.model.ToDoList;
import com.example.team5_project.model.ToDoListDTO;
import com.example.team5_project.model.User;
import com.example.team5_project.repository.CategoryNameRepository;
import com.example.team5_project.repository.ProjectRepository;
import com.example.team5_project.repository.ProjectUserRepository;
import com.example.team5_project.repository.ToDoListRepository;
import com.example.team5_project.repository.mapping.GetProjectMapping;
import com.example.team5_project.repository.mapping.GetUserMapping;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToDoListService {
    private final ToDoListRepository toDoListRepository;
    private final CategoryNameRepository categoryNameRepository;
    private final ProjectRepository projectRepository;
    private final ProjectUserRepository projectUserRepository;

    @Transactional
    public ToDoList createList(ToDoListDTO toDoListDTO, Optional<User> user) {

    	CategoryName categoryName = categoryNameRepository.findByCategoryName(toDoListDTO.getCategory()).get();
		if (categoryName == null) {
			throw new RuntimeException("없는 카테고리입니다.");
		}
		
		GetProjectMapping projectMapping = projectUserRepository.findFirstByUser(user.get());
		Project project = projectMapping.getProject();

		Optional<ToDoList> parent = toDoListRepository.findById(toDoListDTO.getParentId());
		
    	ToDoList toDoList = ToDoList.builder()
    							.parent(parent.isPresent() ? parent.get() : null)
    							.listName(toDoListDTO.getListName())
    							.listDesc(toDoListDTO.getListDesc())
    							.createDate(new Date())
    							.endDate(java.sql.Timestamp.valueOf(toDoListDTO.getEndDate()))
    							.isCommpleted(false)
    							.level(toDoListDTO.getLevel())
    							.user(user.get())
    							.project(project)
    							.categoryName(categoryName)
    							.likeUsers(null)
    							.build();
    							

        return toDoListRepository.save(toDoList);
    }
    
    
    @Transactional
    public ToDoList createProjectList(ToDoListDTO toDoListDTO, Long projectId, Optional<User> user) {

    	CategoryName categoryName = categoryNameRepository.findByCategoryName(toDoListDTO.getCategory()).get();
		if (categoryName == null) {
			throw new RuntimeException("없는 카테고리입니다.");
		}
		
		Project project = projectRepository.findById(projectId).get();

		Optional<ToDoList> parent = toDoListRepository.findById(toDoListDTO.getParentId());
		
    	ToDoList toDoList = ToDoList.builder()
    							.parent(parent.isPresent() ? parent.get() : null)
    							.listName(toDoListDTO.getListName())
    							.listDesc(toDoListDTO.getListDesc())
    							.createDate(new Date())
    							.endDate(java.sql.Timestamp.valueOf(toDoListDTO.getEndDate()))
    							.isCommpleted(false)
    							.level(toDoListDTO.getLevel())
    							.user(user.get())
    							.project(project)
    							.categoryName(categoryName)
    							.likeUsers(null)
    							.build();
    							

        return toDoListRepository.save(toDoList);
    }
    
    
    
    @Transactional
    public void deleteList(Long listId, Optional<User> user) {
    	
    	Optional<ToDoList> todoList = toDoListRepository.findById(listId);


    	Set<GetUserMapping> usersMapping = projectUserRepository.findUserByProject(todoList.get().getProject());

    	Set<User> users = usersMapping.stream()
    	        .map(GetUserMapping::getUser) // 또는 각 객체에서 User를 추출하는 방법으로 변경
    	        .collect(Collectors.toSet());
    	
		if (!users.contains(user.get())) {
			throw new RuntimeException("해당 이름의 사용자가 프로젝트에 속해있지 않습니다.");
		}
		
        toDoListRepository.deleteById(listId);
    }
    
    @Transactional
    public ToDoList updateList(Long listId, ToDoListDTO toDoListDTO, Optional<User> user) {
    	
    	Optional<ToDoList> toDoList = toDoListRepository.findById(listId);
    	Set<GetUserMapping> usersMapping = projectUserRepository.findUserByProject(toDoList.get().getProject());

    	Set<User> users = usersMapping.stream()
    	        .map(GetUserMapping::getUser) // 또는 각 객체에서 User를 추출하는 방법으로 변경
    	        .collect(Collectors.toSet());
    	
		if (!users.contains(user.get())) {
			throw new RuntimeException("해당 이름의 사용자가 프로젝트에 속해있지 않습니다.");
		}
	
    	CategoryName categoryName = CategoryName.builder()
				.categoryName(toDoListDTO.getCategory())
				.id(categoryNameRepository.findByCategoryName(toDoListDTO.getCategory()).get().getId())
				.build();
    	
        // listName, listDesc, endDate, iscompleted, level 수정
        toDoList.get().setListName(toDoListDTO.getListName());
        toDoList.get().setListDesc(toDoListDTO.getListDesc());
        toDoList.get().setEndDate(java.sql.Timestamp.valueOf(toDoListDTO.getEndDate()));
        toDoList.get().setIsCommpleted(toDoListDTO.getIsCommpleted());
        toDoList.get().setCategoryName(categoryName);
        
        // project, categoryName, emotions 수정은 못함

        // Save the updated ToDoList
        return toDoListRepository.save(toDoList.get());
    }

    @Transactional(readOnly = true)
    public ToDoList readList(Long listId, Optional<User> user) {
      	Optional<ToDoList> toDoList = toDoListRepository.findById(listId);
      	
    	Set<GetUserMapping> usersMapping = projectUserRepository.findUserByProject(toDoList.get().getProject());

    	Set<User> users = usersMapping.stream()
    	        .map(GetUserMapping::getUser) // 또는 각 객체에서 User를 추출하는 방법으로 변경
    	        .collect(Collectors.toSet());
    	  
		if (!users.contains(user.get())) {
			throw new RuntimeException("해당 이름의 사용자가 프로젝트에 속해있지 않습니다.");
		}
		
        return toDoList.get();
    }
    
    @Transactional(readOnly = true)
    public Set<ToDoList> readList(Optional<User> user) {
       	Set<ToDoList> toDoLists = toDoListRepository.findAllByUser(user.get());

        return toDoLists;
    }
    
    @Transactional(readOnly = true)
    public Set<ToDoList> readList(Project project) {
       	Set<ToDoList> toDoLists = toDoListRepository.findAllByProject(project);

        return toDoLists;
    }
    
    @Transactional(readOnly = true)
    public List<ToDoList> todayList(Project project) {
        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrowMidnight = calendar.getTime();
        System.out.println(tomorrowMidnight);
        
    	List<ToDoList> toDoLists = toDoListRepository.findAllByProjectAndEndDateBefore(project, tomorrowMidnight);

        return toDoLists;
    }
    
    @Transactional(readOnly = true)
    public List<ToDoList> upcomingList(Project project) {
    	
        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrowMidnight = calendar.getTime();
        System.out.println(tomorrowMidnight);
        
    	List<ToDoList> toDoLists = toDoListRepository.findAllByProjectAndEndDateAfter(project, tomorrowMidnight);

    	return toDoLists;
    }
    
    @Transactional(readOnly = true)
    public Set<ToDoList> readParentList(Long parentId, Optional<User> user) {
       	Set<ToDoList> toDoLists = toDoListRepository.findByParentId(parentId);
       	
        return toDoLists;
    }
    
    public List<Set<ToDoList>> searchLists(String keyword, Optional<User> user) {
    	List<Set<ToDoList>> toDoLists = new ArrayList<>();
    	
    	Set<ProjectUser> projectUsers = projectUserRepository.findByUser(user.get());
    	for(ProjectUser projectUser : projectUsers) {
    		
    		toDoLists.add(toDoListRepository.findByProjectAndListNameContaining(projectUser.getProject(), keyword));
    		toDoLists.add(toDoListRepository.findByProjectAndListDescContaining(projectUser.getProject(), keyword));
    	}
    	
        return toDoLists;
    }
}
