package com.example.demo.service;

import com.example.demo.dto.OportunidadRequest;
import com.example.demo.dto.OportunidadResponse;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.Oportunidad;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.OportunidadMapper;
import com.example.demo.repository.OportunidadRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OportunidadService {

    private final OportunidadRepository oportunidadRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClienteService clienteService;
    private final OportunidadMapper oportunidadMapper;

    public OportunidadService(
            OportunidadRepository oportunidadRepository,
            UsuarioRepository usuarioRepository,
            ClienteService clienteService,
            OportunidadMapper oportunidadMapper
    ) {
        this.oportunidadRepository = oportunidadRepository;
        this.usuarioRepository = usuarioRepository;
        this.clienteService = clienteService;
        this.oportunidadMapper = oportunidadMapper;
    }

    @Transactional(readOnly = true)
    public List<OportunidadResponse> findAll() {
        return oportunidadRepository.findAll().stream()
                .map(oportunidadMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OportunidadResponse findById(Long id) {
        return oportunidadMapper.toResponse(findEntity(id));
    }

    @Transactional(readOnly = true)
    public List<OportunidadResponse> findByClienteId(Long clienteId) {
        clienteService.findEntity(clienteId);
        return oportunidadRepository.findByClienteId(clienteId).stream()
                .map(oportunidadMapper::toResponse)
                .toList();
    }

    public OportunidadResponse create(Long clienteId, OportunidadRequest request) {
        Cliente cliente = clienteService.findEntity(clienteId);
        Usuario responsable = findUsuarioIfPresent(request.responsableId());
        Oportunidad oportunidad = oportunidadMapper.toEntity(request, cliente, responsable);
        return oportunidadMapper.toResponse(oportunidadRepository.save(oportunidad));
    }

    public OportunidadResponse update(Long id, Long clienteId, OportunidadRequest request) {
        Oportunidad oportunidad = findEntity(id);
        Cliente cliente = clienteService.findEntity(clienteId);
        Usuario responsable = findUsuarioIfPresent(request.responsableId());
        oportunidadMapper.updateEntity(oportunidad, request, cliente, responsable);
        return oportunidadMapper.toResponse(oportunidadRepository.save(oportunidad));
    }

    public void delete(Long id) {
        oportunidadRepository.delete(findEntity(id));
    }

    @Transactional(readOnly = true)
    public Oportunidad findEntity(Long id) {
        return oportunidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oportunidad not found with id " + id));
    }

    private Usuario findUsuarioIfPresent(Long responsableId) {
        if (responsableId == null) {
            return null;
        }
        return usuarioRepository.findById(responsableId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario not found with id " + responsableId));
    }
}
