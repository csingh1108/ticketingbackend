package com.graphql.practice.controller;

import com.graphql.practice.domain.User;
import com.graphql.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @QueryMapping
    public User getUser(@Argument Long id){
        return userService.getUser(id);
    }

    @MutationMapping
    public User createUser(
            @Argument String firstName,
            @Argument String lastName,
            @Argument String title,
            @Argument String email
    ) {
        return userService.createUser(firstName, lastName, title, email);
    }

    @MutationMapping
    public User updateUser(
            @Argument Long userId,
            @Argument String firstName,
            @Argument String lastName,
            @Argument String title,
            @Argument String email
    ) {
        return userService.updateUser(userId, firstName, lastName, title, email);
    }

    @MutationMapping
    public User associateTicketWithUser(
            @Argument Long userId,
            @Argument Long ticketId
    ) {
        // Implement logic to associate a ticket with a user and return the updated user
        return userService.associateTicketWithUser(userId, ticketId);
    }

    @MutationMapping
    public User dissociateTicketFromUser(
            @Argument Long userId,
            @Argument Long ticketId
    ) {
        // Implement logic to dissociate a ticket from a user and return the updated user
        return userService.dissociateTicketFromUser(userId, ticketId);
    }

    @MutationMapping
    public String deleteUser(@Argument Long userId) {
        try {
            userService.deleteUser(userId);
            return "User was successfully deleted";
        } catch (Exception e) {
            return "There was an error: " + e.getMessage();
        }
    }
}
