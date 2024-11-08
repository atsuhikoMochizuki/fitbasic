package fr.mochizuki.generic_api.dto;

public record SignInResponseDto(
                String bearer,
                String refresh) {}
