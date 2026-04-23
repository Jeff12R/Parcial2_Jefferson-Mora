package com.example.demo.dto;

import com.example.demo.entity.Role;

public record UsuarioResponse(
        Long id,
        String nombreCompleto,
        String email,
        Role role
) {
}
