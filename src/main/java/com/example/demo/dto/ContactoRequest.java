package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactoRequest(
        @NotBlank String nombre,
        @NotBlank @Email String email,
        @Size(max = 20) String telefono,
        String cargo
) {
}
