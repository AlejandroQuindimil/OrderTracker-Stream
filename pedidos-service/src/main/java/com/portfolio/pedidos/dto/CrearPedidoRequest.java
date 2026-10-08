package com.portfolio.pedidos.dto;

import jakarta.validation.constraints.NotBlank;

public record CrearPedidoRequest(
        @NotBlank String clienteId,
        @NotBlank String direccionOrigen,
        @NotBlank String direccionDestino) {
}