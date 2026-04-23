package com.example.demo.service;

import com.example.demo.dto.ClienteRequest;
import com.example.demo.dto.ClienteResponse;
import com.example.demo.entity.Cliente;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ClienteMapper;
import com.example.demo.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Transactional(readOnly = true)
    public List<ClienteResponse> findAll() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponse findById(Long id) {
        return clienteMapper.toResponse(findEntityWithDetails(id));
    }

    public ClienteResponse create(ClienteRequest request) {
        validateEmail(request.email(), null);
        Cliente cliente = clienteMapper.toEntity(request);
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    public ClienteResponse update(Long id, ClienteRequest request) {
        Cliente cliente = findEntityWithDetails(id);
        validateEmail(request.email(), id);
        clienteMapper.updateEntity(cliente, request);
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    public void delete(Long id) {
        Cliente cliente = findEntityWithDetails(id);
        clienteRepository.delete(cliente);
    }

    @Transactional(readOnly = true)
    public Cliente findEntity(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente not found with id " + id));
    }

    @Transactional(readOnly = true)
    public Cliente findEntityWithDetails(Long id) {
        return clienteRepository.findWithDetailsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente not found with id " + id));
    }

    private void validateEmail(String email, Long currentId) {
        boolean exists = currentId == null
                ? clienteRepository.existsByEmail(email)
                : clienteRepository.existsByEmailAndIdNot(email, currentId);
        if (exists) {
            throw new BusinessException("A client with that email already exists");
        }
    }
}
