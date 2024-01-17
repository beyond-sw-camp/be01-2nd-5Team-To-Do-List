package com.example.team5_project.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EMOTION_REPLY") 
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EmotionReply {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    @Column(name = "emotion_reply_id")
    private Long id;
	
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "reply_id")
    private Reply reply;
    
    @ManyToOne
    @JoinColumn(name = "list_id")
    private ToDoList list;
    
    @ManyToOne
    @JoinColumn(name = "emotion_id")
    private Emotion emotion;
 
}
