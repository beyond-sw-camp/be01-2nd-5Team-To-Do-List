package com.example.team5_project.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToDoListDTO {
	
	private Long parentId;
	
    private String listName;

    private String listDesc;

//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime endDate;
    
    private Boolean isCommpleted;
    
    private Integer level; 
    
    private String category;
}
