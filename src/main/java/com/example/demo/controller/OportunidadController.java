package com.example.demo.controller;

import com.example.demo.dto.OportunidadRequest;
import com.example.demo.dto.OportunidadResponse;
import com.example.demo.service.OportunidadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OportunidadController {

    private final OportunidadService oportunidadService;

    public OportunidadController(OportunidadService oportunidadService) {
        this.oportunidadService = oportunidadService;
    }

    @GetMapping("/api/oportunidades")
    public List<OportunidadResponse> findAll() {
        return oportunidadService.findAll();
    }

    @GetMapping("/api/oportunidades/{id}")
    public OportunidadResponse findById(@PathVariable Long id) {
        return oportunidadService.findById(id);
    }

    @GetMapping("/api/clientes/{clienteId}/oportunidades")
    public List<OportunidadResponse> findByClienteId(@PathVariable Long clienteId) {
        return oportunidadService.findByClienteId(clienteId);
    }

    @PostMapping("/api/clientes/{clienteId}/oportunidades")
    @ResponseStatus(HttpStatus.CREATED)
    public OportunidadResponse create(@PathVariable Long clienteId, @Valid @RequestBody OportunidadRequest request) {
        return oportunidadService.create(clienteId, request);
    }

    @PutMapping("/api/oportunidades/{id}/cliente/{clienteId}")
    public OportunidadResponse update(
            @PathVariable Long id,
            @PathVariable Long clienteId,
            @Valid @RequestBody OportunidadRequest request
    ) {
        return oportunidadService.update(id, clienteId, request);
    }

    @DeleteMapping("/api/oportunidades/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        oportunidadService.delete(id);
    }
}
