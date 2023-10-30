package com.graphql.practice.domain;

import com.graphql.practice.enums.PriorityEnum;
import com.graphql.practice.enums.SourceEnum;
import com.graphql.practice.enums.StatusEnum;
import com.graphql.practice.enums.TypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ticket {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    private StatusEnum status;
    private String details;
    private LocalDateTime submittedDate;
    private LocalDateTime lastUpdatedDate;
    private LocalDateTime closedDate;
    private TypeEnum ticketType;
    private String description;

    @OneToMany(mappedBy = "ticket" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    private PriorityEnum priority;
    private SourceEnum source;

    @ManyToOne(fetch = FetchType.LAZY)
    private User assignedTo;

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", status=" + status +
                ", submittedDate='" + submittedDate + '\'' +
                ", lastUpdatedDate='" + lastUpdatedDate + '\'' +
                ", ticketType='" + ticketType + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
