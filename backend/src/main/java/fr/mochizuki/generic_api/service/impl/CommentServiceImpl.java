package fr.mochizuki.generic_api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.util.Streamable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import fr.mochizuki.generic_api.cross_cutting.exceptions.InoteEmptyMessageCommentException;
import fr.mochizuki.generic_api.dto.CommentResponseDto;
import fr.mochizuki.generic_api.entity.Comment;
import fr.mochizuki.generic_api.entity.User;
import fr.mochizuki.generic_api.repository.CommentRepository;
import fr.mochizuki.generic_api.repository.UserRepository;
import fr.mochizuki.generic_api.service.CommentService;

/**
 * The Service CommentServiceImpl
 * 
 * @author A.Mochizuki
 * @date 11/04/2024
 */
@Service
public class CommentServiceImpl implements CommentService {

    /* DEPENDENCIES INJECTION */
    /* ============================================================ */
    private CommentRepository commentRepository;
    private UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    /* PUBLIC METHODS */
    /* ============================================================ */

    /**
     * create a comment
     * 
     * @param msg
     * @return Comment
     * @throws InoteEmptyMessageCommentException
     * 
     * @author A.Mochizuki
     * @date 11/04/2024
     */
    public Comment createComment(String msg) throws InoteEmptyMessageCommentException {
        // Get current user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (msg.isBlank() || msg.isEmpty()) {
            throw new InoteEmptyMessageCommentException();
        }
        User foundedUser = userRepository.findByEmail(user.getEmail()).orElseThrow();
        Comment commentToWrite = Comment.builder()
                .user(foundedUser)
                .message(msg)
                .build();
        return this.commentRepository.save(commentToWrite);
    }

    /**
     * Get the list of all comments recorded
     *
     * @return the list of all comments in database
     * @author A.Mochizuki
     * @date 11/04/2024
     */
    public List<CommentResponseDto> getAll() {
        List<CommentResponseDto> commentDtos = new ArrayList<>();
        List<Comment> comments =Streamable.of(this.commentRepository.findAll()).toList();
        for(Comment item : comments){
            commentDtos.add(new CommentResponseDto(item.getId(), item.getMessage(), item.getUser().getId()));
        }
        return  commentDtos;
    }
}
