package com.portfolio.pedidos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pedido {

    @Id
    private UUID id;
    private String clienteId;
    private String direccionOrigen;
    private String direccionDestino;
    private long creadoEn;
}