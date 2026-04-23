package com.example.demo.mapper;

import com.example.demo.dto.ContactoRequest;
import com.example.demo.dto.ContactoResponse;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.Contacto;
import org.springframework.stereotype.Component;

@Component
public class ContactoMapper {

    public Contacto toEntity(ContactoRequest request, Cliente cliente) {
        return Contacto.builder()
                .nombre(request.nombre())
                .email(request.email())
                .telefono(request.telefono())
                .cargo(request.cargo())
                .cliente(cliente)
                .build();
    }

    public void updateEntity(Contacto entity, ContactoRequest request, Cliente cliente) {
        entity.setNombre(request.nombre());
        entity.setEmail(request.email());
        entity.setTelefono(request.telefono());
        entity.setCargo(request.cargo());
        entity.setCliente(cliente);
    }

    public ContactoResponse toResponse(Contacto entity) {
        return new ContactoResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getEmail(),
                entity.getTelefono(),
                entity.getCargo(),
                entity.getCliente() != null ? entity.getCliente().getId() : null
        );
    }
}
