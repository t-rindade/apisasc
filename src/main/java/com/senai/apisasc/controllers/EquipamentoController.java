package com.senai.apisasc.controllers;


import com.senai.apisasc.dtos.EquipamentoDto;
import com.senai.apisasc.models.EquipamentoModel;
import com.senai.apisasc.repositories.EquipamentoRepository;
import com.senai.apisasc.repositories.FabricanteRepository;
import com.senai.apisasc.repositories.SetorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/equipamento", produces = {"application/json"})
public class EquipamentoController {
    @Autowired
    EquipamentoRepository equipamentoRepository;

    @Autowired
    SetorRepository setorRepository;

    @Autowired
    FabricanteRepository fabricanteRepository;

    @GetMapping
    public ResponseEntity<List<EquipamentoModel>> listarEquipamentos() {
        return ResponseEntity.status(HttpStatus.OK).body(equipamentoRepository.findAll());
    }

    @GetMapping("/{idEquipamento}")
    public ResponseEntity<Object> exibirEquipamentos(@PathVariable(value = "idEquipamento")UUID id) {
        Optional<EquipamentoModel> equipamentoBuscado = equipamentoRepository.findById(id);

        if (equipamentoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipamento nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(equipamentoBuscado.get());
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarEquipamento(@RequestBody @Valid EquipamentoDto equipamentoDto) {
        EquipamentoModel equipamentoModel = new EquipamentoModel();
        BeanUtils.copyProperties(equipamentoDto, equipamentoModel);

        var setor = setorRepository.findById(equipamentoDto.id_setor());
        var fabricante = fabricanteRepository.findById(equipamentoDto.id_fabricante());

        if (setor.isPresent()) {
            equipamentoModel.setSetor(setor.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_setor nao encontrado");
        }

        if (fabricante.isPresent()) {
            equipamentoModel.setFabricante(fabricante.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_fabricante nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(equipamentoRepository.save(equipamentoModel));
    }

    @PutMapping(value = "/{idEquipamento}")
    public ResponseEntity<Object> editarEquipamento(@PathVariable(value = "idEquipamento") UUID id, @RequestBody @Valid EquipamentoDto equipamentoDto) {
        Optional<EquipamentoModel> equipamentoBuscado = equipamentoRepository.findById(id);

        if (equipamentoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipamento nao encontrado");
        }

        EquipamentoModel equipamento = equipamentoBuscado.get();
        BeanUtils.copyProperties(equipamentoDto, equipamento);

        var setor = setorRepository.findById(equipamentoDto.id_setor());
        var fabricante = fabricanteRepository.findById(equipamentoDto.id_fabricante());

        if (setor.isPresent()) {
            equipamento.setSetor(setor.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_setor nao encontrado");
        }

        if (fabricante.isPresent()) {
            equipamento.setFabricante(fabricante.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_fabricante nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(equipamentoRepository.save(equipamento));
    }

    @DeleteMapping("/{idEquipamento}")
    public ResponseEntity<Object> deleterEquipamento(@PathVariable(value = "idEquipamento") UUID id) {
        Optional<EquipamentoModel> equipamentoBuscado = equipamentoRepository.findById(id);

        if (equipamentoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipamento nao encotrado");
        }

        equipamentoRepository.delete(equipamentoBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Equipamento deletado com sucesso");
    }
}
