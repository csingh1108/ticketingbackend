package com.graphql.practice.service;

import com.graphql.practice.domain.Comment;
import com.graphql.practice.domain.Ticket;
import com.graphql.practice.domain.User;
import com.graphql.practice.repository.CommentRepository;
import com.graphql.practice.repository.TicketRepository;
import com.graphql.practice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment getComment(Long id) {
        Optional<Comment> commentOpt =  commentRepository.findById(id);
        if(commentOpt.isPresent()){
            return commentOpt.get();
        } else {
            throw new NoSuchElementException("Comment with that id not found.");
        }
    }

    public List<Comment> getAllComments(Long ticketId) {
        return commentRepository.findAllByTicketTicketId(ticketId);
    }

    public Comment createComment(String content, Long authorId, Long ticketId) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);

        Optional< User> userOpt = userRepository.findById(authorId);

        if(ticketOpt.isPresent()){
            if(userOpt.isPresent()){
                Comment comment = new Comment();
                comment.setTicket(ticketOpt.get());
                comment.setContent(content);
                comment.setAuthor(userOpt.get());
                comment.setTimePosted(LocalDateTime.now());
                return commentRepository.save(comment);
            }else{
                throw new NoSuchElementException("User error.");
            }
        }else{
            throw new NoSuchElementException("Ticket for associated comment not found.");
        }
    }

    public Comment updateComment(Long commentId, String content) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if(commentOpt.isPresent()){
            Comment updatedComment = commentOpt.get();
            updatedComment.setContent(content);
            updatedComment.setUpdatedTime(LocalDateTime.now());
            return commentRepository.save(updatedComment);
        }else{
            throw new NoSuchElementException("There was an error updating the comment. Associated Ticket not found.");
        }
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
