package com.example.demo.dto;

public record ContactoResponse(
        Long id,
        String nombre,
        String email,
        String telefono,
        String cargo,
        Long clienteId
) {
}
