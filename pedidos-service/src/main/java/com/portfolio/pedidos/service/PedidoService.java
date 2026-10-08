package com.portfolio.pedidos.service;

import com.portfolio.pedidos.dto.CrearPedidoRequest;
import com.portfolio.pedidos.kafka.PedidoCreado;
import com.portfolio.pedidos.kafka.PedidoProducer;
import com.portfolio.pedidos.model.Pedido;
import com.portfolio.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final PedidoProducer producer;

    @Transactional
    public Pedido crear(CrearPedidoRequest request) {
        Pedido pedido = new Pedido();
        pedido.setId(UUID.randomUUID());
        pedido.setClienteId(request.clienteId());
        pedido.setDireccionOrigen(request.direccionOrigen());
        pedido.setDireccionDestino(request.direccionDestino());
        pedido.setCreadoEn(System.currentTimeMillis());

        repository.save(pedido);

        producer.publicar(new PedidoCreado(
                "PEDIDO_CREADO",
                pedido.getId(),
                pedido.getClienteId(),
                pedido.getDireccionOrigen(),
                pedido.getDireccionDestino(),
                pedido.getCreadoEn()));

        return pedido;
    }

    public List<Pedido> listar() {
        return repository.findAll();
    }

    public Pedido obtener(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El pedido no existe"));
    }
}