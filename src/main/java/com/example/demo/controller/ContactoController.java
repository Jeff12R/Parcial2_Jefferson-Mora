package com.example.demo.controller;

import com.example.demo.dto.ContactoRequest;
import com.example.demo.dto.ContactoResponse;
import com.example.demo.service.ContactoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactoController {

    private final ContactoService contactoService;

    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    @GetMapping("/api/contactos")
    public List<ContactoResponse> findAll() {
        return contactoService.findAll();
    }

    @GetMapping("/api/contactos/{id}")
    public ContactoResponse findById(@PathVariable Long id) {
        return contactoService.findById(id);
    }

    @GetMapping("/api/clientes/{clienteId}/contactos")
    public List<ContactoResponse> findByClienteId(@PathVariable Long clienteId) {
        return contactoService.findByClienteId(clienteId);
    }

    @PostMapping("/api/clientes/{clienteId}/contactos")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactoResponse create(@PathVariable Long clienteId, @Valid @RequestBody ContactoRequest request) {
        return contactoService.create(clienteId, request);
    }

    @PutMapping("/api/contactos/{id}/cliente/{clienteId}")
    public ContactoResponse update(
            @PathVariable Long id,
            @PathVariable Long clienteId,
            @Valid @RequestBody ContactoRequest request
    ) {
        return contactoService.update(id, clienteId, request);
    }

    @DeleteMapping("/api/contactos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        contactoService.delete(id);
    }
}
