package com.portfolio.pedidos.controller;

import com.portfolio.pedidos.dto.CrearPedidoRequest;
import com.portfolio.pedidos.model.Pedido;
import com.portfolio.pedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido crear(@Valid @RequestBody CrearPedidoRequest request) {
        return service.crear(request);
    }

    @GetMapping
    public List<Pedido> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Pedido obtener(@PathVariable UUID id) {
        return service.obtener(id);
    }
}