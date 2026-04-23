package com.example.demo.mapper;

import com.example.demo.dto.OportunidadRequest;
import com.example.demo.dto.OportunidadResponse;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.Oportunidad;
import com.example.demo.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class OportunidadMapper {

    public Oportunidad toEntity(OportunidadRequest request, Cliente cliente, Usuario responsable) {
        return Oportunidad.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .valor(request.valor())
                .estado(request.estado())
                .fechaCierre(request.fechaCierre())
                .cliente(cliente)
                .responsable(responsable)
                .build();
    }

    public void updateEntity(Oportunidad entity, OportunidadRequest request, Cliente cliente, Usuario responsable) {
        entity.setNombre(request.nombre());
        entity.setDescripcion(request.descripcion());
        entity.setValor(request.valor());
        entity.setEstado(request.estado());
        entity.setFechaCierre(request.fechaCierre());
        entity.setCliente(cliente);
        entity.setResponsable(responsable);
    }

    public OportunidadResponse toResponse(Oportunidad entity) {
        return new OportunidadResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getValor(),
                entity.getEstado(),
                entity.getFechaCierre(),
                entity.getCliente() != null ? entity.getCliente().getId() : null,
                entity.getResponsable() != null ? entity.getResponsable().getId() : null
        );
    }
}
