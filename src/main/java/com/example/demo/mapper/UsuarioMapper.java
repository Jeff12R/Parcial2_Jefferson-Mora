package com.example.demo.mapper;

import com.example.demo.dto.UsuarioRequest;
import com.example.demo.dto.UsuarioResponse;
import com.example.demo.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequest request) {
        return Usuario.builder()
                .nombreCompleto(request.nombreCompleto())
                .email(request.email())
                .password(request.password())
                .role(request.role())
                .build();
    }

    public void updateEntity(Usuario entity, UsuarioRequest request) {
        entity.setNombreCompleto(request.nombreCompleto());
        entity.setEmail(request.email());
        entity.setPassword(request.password());
        entity.setRole(request.role());
    }

    public UsuarioResponse toResponse(Usuario entity) {
        return new UsuarioResponse(
                entity.getId(),
                entity.getNombreCompleto(),
                entity.getEmail(),
                entity.getRole()
        );
    }
}
