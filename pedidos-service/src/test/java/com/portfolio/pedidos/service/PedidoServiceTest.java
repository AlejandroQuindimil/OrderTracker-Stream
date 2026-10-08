package com.portfolio.pedidos.service;

import com.portfolio.pedidos.dto.CrearPedidoRequest;
import com.portfolio.pedidos.kafka.PedidoCreado;
import com.portfolio.pedidos.kafka.PedidoProducer;
import com.portfolio.pedidos.model.Pedido;
import com.portfolio.pedidos.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository repository;

    @Mock
    private PedidoProducer producer;

    @InjectMocks
    private PedidoService service;

    @Test
    void crear_guardaElPedidoYPublicaElEvento() {
        var request = new CrearPedidoRequest("c1", "Origen", "Destino");

        Pedido resultado = service.crear(request);

        assertNotNull(resultado.getId());
        assertEquals("c1", resultado.getClienteId());
        verify(repository).save(resultado);

        ArgumentCaptor<PedidoCreado> captor = ArgumentCaptor.forClass(PedidoCreado.class);
        verify(producer).publicar(captor.capture());
        assertEquals("PEDIDO_CREADO", captor.getValue().tipo());
        assertEquals(resultado.getId(), captor.getValue().pedidoId());
    }

    @Test
    void obtener_lanzaNotFoundSiNoExiste() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.obtener(id));
    }
}