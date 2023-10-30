package com.graphql.practice.controller;

import com.graphql.practice.domain.Ticket;
import com.graphql.practice.enums.PriorityEnum;
import com.graphql.practice.enums.SourceEnum;
import com.graphql.practice.enums.StatusEnum;
import com.graphql.practice.enums.TypeEnum;
import com.graphql.practice.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @QueryMapping
    public Ticket getTicket(@Argument Long ticketId){
        return ticketService.getTicketById(ticketId);
    }

    @QueryMapping
    public List<Ticket> getAllTickets(){
        return ticketService.getAllTickets();
    }

    @MutationMapping
    public Ticket createTicket(
            @Argument StatusEnum status,
            @Argument String details,
            @Argument PriorityEnum priority,
            @Argument SourceEnum source,
            @Argument Long assignedTo,
            @Argument TypeEnum ticketType,
            @Argument String description
    ) {
        return ticketService.createTicket(status, details, priority, source, assignedTo, ticketType, description);
    }

    @MutationMapping
    public Ticket updateTicket(
            @Argument Long ticketId,
            @Argument StatusEnum status,
            @Argument String details,
            @Argument PriorityEnum priority,
            @Argument SourceEnum source,
            @Argument Long assignedTo,
            @Argument TypeEnum ticketType,
            @Argument String description
    ) {
        return ticketService.updateTicket(ticketId, status, details, priority, source, assignedTo, ticketType, description);
    }

    @MutationMapping
    public Ticket closeTicket(
            @Argument Long ticketId
    ){
        return ticketService.closeTicket(ticketId);
    }

    @MutationMapping
    public String deleteTicket(@Argument Long ticketId){
        try{
            ticketService.deleteById(ticketId);
            return "Ticket was successfully deleted";
        } catch(Exception e) {
            return "There was an error: "+ e.getMessage();
        }

    }

}
