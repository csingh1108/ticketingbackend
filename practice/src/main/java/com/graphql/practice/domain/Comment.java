package com.graphql.practice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private LocalDateTime timePosted;
    private LocalDateTime updatedTime;
    private String content;

    @ManyToOne
    private User author;

    @ManyToOne
    @JoinColumn(name = "ticketId")
    private Ticket ticket;

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", timePosted=" + timePosted +
                ", content='" + content + '\'' +
                ", updatedTime='" + updatedTime + '\'' +
                '}';
    }
}
