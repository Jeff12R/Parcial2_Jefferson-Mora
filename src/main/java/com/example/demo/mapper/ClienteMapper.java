package com.example.demo.mapper;

import com.example.demo.dto.ClienteRequest;
import com.example.demo.dto.ClienteResponse;
import com.example.demo.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    private final ContactoMapper contactoMapper;
    private final OportunidadMapper oportunidadMapper;

    public ClienteMapper(ContactoMapper contactoMapper, OportunidadMapper oportunidadMapper) {
        this.contactoMapper = contactoMapper;
        this.oportunidadMapper = oportunidadMapper;
    }

    public Cliente toEntity(ClienteRequest request) {
        return Cliente.builder()
                .nombre(request.nombre())
                .email(request.email())
                .telefono(request.telefono())
                .empresa(request.empresa())
                .notas(request.notas())
                .build();
    }

    public void updateEntity(Cliente entity, ClienteRequest request) {
        entity.setNombre(request.nombre());
        entity.setEmail(request.email());
        entity.setTelefono(request.telefono());
        entity.setEmpresa(request.empresa());
        entity.setNotas(request.notas());
    }

    public ClienteResponse toResponse(Cliente entity) {
        return new ClienteResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getEmail(),
                entity.getTelefono(),
                entity.getEmpresa(),
                entity.getNotas(),
                entity.getContactos().stream().map(contactoMapper::toResponse).toList(),
                entity.getOportunidades().stream().map(oportunidadMapper::toResponse).toList()
        );
    }
}
