package com.senai.apisasc.controllers;


import com.senai.apisasc.models.FuncionarioModel;
import com.senai.apisasc.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/funcionarios", produces = {"application/json"})
public class FuncionarioController {
    @Autowired
    FuncionarioRepository funcionarioRepository;

    @GetMapping
    public ResponseEntity<List<FuncionarioModel>> listarFuncionarios() {
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioRepository.findAll());
    }

    @GetMapping("/{idFuncionario}")
    public ResponseEntity<Object> exibirFuncionario(@PathVariable(value = "idFuncionario") UUID id) {
        Optional<FuncionarioModel> funcionarioBuscado = funcionarioRepository.findById(id);

        if (funcionarioBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(funcionarioBuscado.get());
    }
}
