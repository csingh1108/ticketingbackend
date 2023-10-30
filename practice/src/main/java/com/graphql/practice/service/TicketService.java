package com.graphql.practice.service;

import com.graphql.practice.domain.Ticket;
import com.graphql.practice.domain.User;
import com.graphql.practice.enums.PriorityEnum;
import com.graphql.practice.enums.SourceEnum;
import com.graphql.practice.enums.StatusEnum;
import com.graphql.practice.enums.TypeEnum;
import com.graphql.practice.repository.TicketRepository;
import com.graphql.practice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    public Ticket getTicketById(Long ticketId) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isPresent()) {
            return ticketOpt.get();
        } else {
            throw new NoSuchElementException("No ticket with that id found.");
        }
    }

    public Ticket createTicket(StatusEnum status, String details, PriorityEnum priority, SourceEnum source, Long assignedTo, TypeEnum ticketType, String description) {
        // Create a new ticket entity
        Ticket newTicket = new Ticket();
        newTicket.setStatus(status);
        newTicket.setDetails(details);
        newTicket.setPriority(priority);
        newTicket.setSource(source);
        User user = findUser(assignedTo);
        newTicket.setAssignedTo(user);
        newTicket.setSubmittedDate(LocalDateTime.now());
        newTicket.setLastUpdatedDate(null);
        newTicket.setTicketType(ticketType);
        newTicket.setDescription(description);


        // Save the new ticket to the database
        return ticketRepository.save(newTicket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket updateTicket(Long ticketId, StatusEnum status, String details, PriorityEnum priority, SourceEnum source, Long assignedTo, TypeEnum ticketType, String description) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();

            // Update fields only if the input is not null
            if (status != null) {
                ticket.setStatus(status);
            }
            if (details != null) {
                ticket.setDetails(details);
            }
            if (priority != null) {
                ticket.setPriority(priority);
            }
            if (source != null) {
                ticket.setSource(source);
            }
            if (assignedTo != null) {
                User user = findUser(assignedTo);
                ticket.setAssignedTo(user);
            }
            if(ticketType != null){
                ticket.setTicketType(ticketType);
            }
            if(description != null){
                ticket.setDescription(description);
            }

            // Set the lastUpdatedDate
            ticket.setLastUpdatedDate(LocalDateTime.now());

            return ticketRepository.save(ticket);
        } else {
            throw new NoSuchElementException("No ticket found with that id.");
        }
    }


    public User findUser(Long id){
        if(id == null){
            return null;
        } else {
            Optional<User> userOpt = userRepository.findById(id);
            if(userOpt.isPresent()){
                User user = userOpt.get();
                return user;
            } else {
                return null;
            }
        }
    }

    public void deleteById(Long ticketId) {
        ticketRepository.deleteById(ticketId);
    }

    public Ticket closeTicket(Long ticketId) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if(ticketOpt.isPresent()){
            Ticket ticket = ticketOpt.get();
            ticket.setStatus(StatusEnum.CLOSED);
            ticket.setClosedDate(LocalDateTime.now());
            return ticketRepository.save(ticket);
        } else {
            throw new NoSuchElementException("No ticket found with that id.");
        }
    }
}
