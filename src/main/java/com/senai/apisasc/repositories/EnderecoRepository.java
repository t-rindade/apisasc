package com.senai.apisasc.repositories;

import com.senai.apisasc.models.EnderecoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<EnderecoModel, UUID> {
}