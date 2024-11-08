package fr.mochizuki.generic_api.dto;

public record CommentResponseDto(
        Integer id,
        String message,
        Integer UserId
) {}
