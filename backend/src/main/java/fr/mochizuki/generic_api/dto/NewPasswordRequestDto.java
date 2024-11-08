package fr.mochizuki.generic_api.dto;

public record NewPasswordRequestDto(
        String email,
        String code,
        String password
) {}




