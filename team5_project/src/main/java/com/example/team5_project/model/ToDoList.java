package com.example.team5_project.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TODOLIST")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ToDoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    @Column(name = "list_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ToDoList parent;

    @NotBlank
    @Size(max = 100)
    @Column(name = "listName")
    private String listName;

    @NotBlank
    @Column(name = "listDesc")
    private String listDesc;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createDate")
    private Date createDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "endDate")
    private Date endDate;
    
    @Column(name = "isCompleted")
    private Boolean isCommpleted;
    
    @Column(name = "level")
    private Integer level; 

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    
    @ManyToOne
    @JoinColumn(name = "categoryname_id")
    private CategoryName categoryName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "LIKEUSER",
            joinColumns = @JoinColumn(name = "list_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> likeUsers = new HashSet<>();
}
