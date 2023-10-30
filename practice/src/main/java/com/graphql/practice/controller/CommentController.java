package com.graphql.practice.controller;

import com.graphql.practice.domain.Comment;
import com.graphql.practice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @QueryMapping
    public Comment getComment(@Argument Long commentId){
        return commentService.getComment(commentId);
    }

    @QueryMapping
    public List<Comment> getAllComments(@Argument Long ticketId){
        return commentService.getAllComments(ticketId);
    }

    @MutationMapping
    public Comment createComment(
            @Argument String content,
            @Argument Long authorId,
            @Argument Long ticketId
    ){
        return commentService.createComment(content, authorId, ticketId);
    }

    @MutationMapping
    public Comment updateComment(
            @Argument Long commentId,
            @Argument String content
    ){
        return commentService.updateComment(commentId, content);
    }

    @MutationMapping
    public String deleteComment(@Argument Long commentId){
        try{
            commentService.deleteComment(commentId);
            return "User was successfully deleted.";
        }catch (Exception e){
            return "There was an error: " + e.getMessage();
        }
    }
}
