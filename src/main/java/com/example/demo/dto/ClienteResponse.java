package com.example.demo.dto;

import java.util.List;

public record ClienteResponse(
        Long id,
        String nombre,
        String email,
        String telefono,
        String empresa,
        String notas,
        List<ContactoResponse> contactos,
        List<OportunidadResponse> oportunidades
) {
}
