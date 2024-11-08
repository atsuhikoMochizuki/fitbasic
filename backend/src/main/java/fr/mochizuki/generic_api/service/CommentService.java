package fr.mochizuki.generic_api.service;

import java.util.List;

import fr.mochizuki.generic_api.cross_cutting.exceptions.InoteEmptyMessageCommentException;
import fr.mochizuki.generic_api.dto.CommentResponseDto;
import fr.mochizuki.generic_api.entity.Comment;

public interface CommentService {

    Comment createComment(String message) throws InoteEmptyMessageCommentException;

    public List<CommentResponseDto> getAll();

}
