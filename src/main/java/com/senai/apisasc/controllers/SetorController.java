package com.senai.apisasc.controllers;

import com.senai.apisasc.models.SetorModel;
import com.senai.apisasc.repositories.SetorRepository;
import com.senai.apisasc.repositories.UnidadeRepository;
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
@RequestMapping(value = "/setor", produces = {"application/json"})
public class SetorController {
    @Autowired
    SetorRepository setorRepository;

    @Autowired
    UnidadeRepository unidadeRepository;

    @GetMapping
    public ResponseEntity<List<SetorModel>> listarSetores() {
        return ResponseEntity.status(HttpStatus.OK).body(setorRepository.findAll());
    }

    @GetMapping("/{idSetor}")
    public ResponseEntity<Object> exibirSetores(@PathVariable(value = "idSetor")UUID id) {
        Optional<SetorModel> setorBuscado = setorRepository.findById(id);

        if (setorBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Setor nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(setorBuscado.get());
    }
}
