package com.portfolio.pedidos.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PedidoProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publicar(PedidoCreado evento) {
        kafkaTemplate.send(KafkaConfig.TOPIC_PEDIDOS, evento.pedidoId().toString(), evento)
                .whenComplete((resultado, error) -> {
                    if (error != null) {
                        log.error("No se pudo publicar el evento del pedido {}", evento.pedidoId(), error);
                    } else {
                        log.info("Evento publicado: pedido {} en partición {}",
                                evento.pedidoId(), resultado.getRecordMetadata().partition());
                    }
                });
    }
}