package com.senai.apisasc.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public record EquipamentoDto(
        @NotBlank String modelo,
        @NotNull BigDecimal valor,
        @NotNull BigDecimal consumo_nominal,
        @NotNull Date data_compra,

        @NotNull UUID id_setor,
        @NotNull UUID id_fabricante
) {
}
