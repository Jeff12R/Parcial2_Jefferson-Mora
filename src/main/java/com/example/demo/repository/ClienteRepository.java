package com.example.demo.repository;

import com.example.demo.entity.Cliente;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    @EntityGraph(attributePaths = {"contactos", "oportunidades"})
    Optional<Cliente> findWithDetailsById(Long id);

    @Override
    @EntityGraph(attributePaths = {"contactos", "oportunidades"})
    List<Cliente> findAll();
}
