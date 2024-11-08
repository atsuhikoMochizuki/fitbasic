package fr.mochizuki.generic_api.dto;

import fr.mochizuki.generic_api.entity.Role;

public record PublicUserResponseDto(
        String username,
        String pseudonyme,
        String avatar,
        boolean actif,
        Role role) {}
