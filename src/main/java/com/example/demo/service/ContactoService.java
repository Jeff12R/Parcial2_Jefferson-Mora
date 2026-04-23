package com.example.demo.service;

import com.example.demo.dto.ContactoRequest;
import com.example.demo.dto.ContactoResponse;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.Contacto;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ContactoMapper;
import com.example.demo.repository.ContactoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContactoService {

    private final ContactoRepository contactoRepository;
    private final ClienteService clienteService;
    private final ContactoMapper contactoMapper;

    public ContactoService(ContactoRepository contactoRepository, ClienteService clienteService, ContactoMapper contactoMapper) {
        this.contactoRepository = contactoRepository;
        this.clienteService = clienteService;
        this.contactoMapper = contactoMapper;
    }

    @Transactional(readOnly = true)
    public List<ContactoResponse> findAll() {
        return contactoRepository.findAll().stream()
                .map(contactoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ContactoResponse findById(Long id) {
        return contactoMapper.toResponse(findEntity(id));
    }

    @Transactional(readOnly = true)
    public List<ContactoResponse> findByClienteId(Long clienteId) {
        clienteService.findEntity(clienteId);
        return contactoRepository.findByClienteId(clienteId).stream()
                .map(contactoMapper::toResponse)
                .toList();
    }

    public ContactoResponse create(Long clienteId, ContactoRequest request) {
        Cliente cliente = clienteService.findEntity(clienteId);
        validateEmail(request.email(), null);
        Contacto contacto = contactoMapper.toEntity(request, cliente);
        return contactoMapper.toResponse(contactoRepository.save(contacto));
    }

    public ContactoResponse update(Long id, Long clienteId, ContactoRequest request) {
        Contacto contacto = findEntity(id);
        Cliente cliente = clienteService.findEntity(clienteId);
        validateEmail(request.email(), id);
        contactoMapper.updateEntity(contacto, request, cliente);
        return contactoMapper.toResponse(contactoRepository.save(contacto));
    }

    public void delete(Long id) {
        contactoRepository.delete(findEntity(id));
    }

    @Transactional(readOnly = true)
    public Contacto findEntity(Long id) {
        return contactoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contacto not found with id " + id));
    }

    private void validateEmail(String email, Long currentId) {
        boolean exists = currentId == null
                ? contactoRepository.existsByEmail(email)
                : contactoRepository.existsByEmailAndIdNot(email, currentId);
        if (exists) {
            throw new BusinessException("A contact with that email already exists");
        }
    }
}
