package com.example.demo.dto;

import com.example.demo.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
        @NotBlank String nombreCompleto,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 4) String password,
        @NotNull Role role
) {
}
