package com.example.demo.dto;

import com.example.demo.entity.EstadoOportunidad;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OportunidadResponse(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal valor,
        EstadoOportunidad estado,
        LocalDate fechaCierre,
        Long clienteId,
        Long responsableId
) {
}
