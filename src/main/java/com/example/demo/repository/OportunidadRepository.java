package com.example.demo.repository;

import com.example.demo.entity.Oportunidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OportunidadRepository extends JpaRepository<Oportunidad, Long> {

    List<Oportunidad> findByClienteId(Long clienteId);
}
