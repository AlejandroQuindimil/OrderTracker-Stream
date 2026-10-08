package com.portfolio.pedidos.kafka;

import java.util.UUID;

public record PedidoCreado(
        String tipo,
        UUID pedidoId,
        String clienteId,
        String direccionOrigen,
        String direccionDestino,
        long timestamp) {
}