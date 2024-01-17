package com.example.team5_project.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.team5_project.Service.ToDoListService;
import com.example.team5_project.Service.UserService;
import com.example.team5_project.model.ToDoList;
import com.example.team5_project.model.ToDoListDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ListController {
	private final UserService userService;
	private final ToDoListService toDoListService;
		
	@PostMapping("/list/create")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<ToDoList> createList(@RequestBody ToDoListDTO toDoListDTO) {
		
		toDoListDTO.setParentId(0L);
		toDoListDTO.setLevel(0);
//		if(toDoListDTO.getParentId() == 0)
//			toDoListDTO.setParentId(-1L);
		
		return ResponseEntity.ok(toDoListService.createList(toDoListDTO, userService.getMyUserWithAuthorities()));
	}
	
	@DeleteMapping("/list/delete/{listId}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> deleteList(@PathVariable Long listId) {
		toDoListService.deleteList(listId, userService.getMyUserWithAuthorities());
        return ResponseEntity.ok(new String("delete list"));
    }
	
    @PutMapping("/list/update/{listId}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<ToDoList> updateList(@PathVariable Long listId, @RequestBody ToDoListDTO updatedToDoListDTO) {
        return ResponseEntity.ok(toDoListService.updateList(listId, updatedToDoListDTO, userService.getMyUserWithAuthorities()));
    }

    @GetMapping("/list/read/{listId}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<ToDoList> readList(@PathVariable Long listId) {
        return ResponseEntity.ok(toDoListService.readList(listId, userService.getMyUserWithAuthorities()));
    }
    
	// 검색
	@GetMapping("/list/search")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<Set<ToDoList>>> searchLists(@RequestParam String keyword) {
        List<Set<ToDoList>> matchingLists = toDoListService.searchLists(keyword, userService.getMyUserWithAuthorities());
        return ResponseEntity.ok(matchingLists);
    }

}
