package com.example.demo.dto;

import com.example.demo.entity.EstadoOportunidad;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OportunidadRequest(
        @NotBlank String nombre,
        String descripcion,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal valor,
        @NotNull EstadoOportunidad estado,
        LocalDate fechaCierre,
        Long responsableId
) {
}
