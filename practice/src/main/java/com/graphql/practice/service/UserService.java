package com.graphql.practice.service;

import com.graphql.practice.domain.Ticket;
import com.graphql.practice.domain.User;
import com.graphql.practice.enums.StatusEnum;
import com.graphql.practice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketService ticketService;

    public User getUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if(userOpt.isPresent()){
            return userOpt.get();
        } else {
            throw new NoSuchElementException("User with id: "+ id +" not found.");
        }
    }

    public User createUser(String firstName, String lastName, String title, String email) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setTitle(title);
        user.setEmail(email);
        return userRepository.save(user);
    }

    public User updateUser(Long userId, String firstName, String lastName, String title, String email) {
        User user = getUser(userId);
        if(firstName != null){
            user.setFirstName(firstName);
        }
        if(lastName != null){
            user.setLastName(lastName);
        }
        if(title != null){
            user.setTitle(title);
        }
        if(email != null){
            user.setEmail(email);
        }
        return userRepository.save(user);
    }

    public User associateTicketWithUser(Long userId, Long ticketId) {
        User user = getUser(userId);

        Ticket ticket = ticketService.getTicketById(ticketId);

        if (user.getAssignedTickets() != null) {
            user.getAssignedTickets().add(ticket);
        } else {
            List<Ticket> assignedTickets = new ArrayList<>();
            assignedTickets.add(ticket);
            user.setAssignedTickets(assignedTickets);
        }

        user.setNumOfTickets(user.getNumOfTickets() + 1);

        if (ticket.getStatus() == StatusEnum.OPEN) {
            user.setNumOfOpenTickets(user.getNumOfOpenTickets() + 1);
        } else if (ticket.getStatus() == StatusEnum.CLOSED) {
            user.setNumOfClosedTickets(user.getNumOfClosedTickets() + 1);
        }

        return user;
    }

    public User dissociateTicketFromUser(Long userId, Long ticketId) {
        User user = getUser(userId);

        Ticket ticket = ticketService.getTicketById(ticketId);

        if (user.getAssignedTickets() != null) {
            user.getAssignedTickets().remove(ticket);
        }

        user.setNumOfTickets(user.getNumOfTickets() - 1);

        if (ticket.getStatus() == StatusEnum.OPEN) {
            user.setNumOfOpenTickets(user.getNumOfOpenTickets() - 1);
        } else if (ticket.getStatus() == StatusEnum.CLOSED) {
            user.setNumOfClosedTickets(user.getNumOfClosedTickets() - 1);
        }

        return user;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
