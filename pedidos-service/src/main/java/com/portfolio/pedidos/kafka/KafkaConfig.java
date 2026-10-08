package com.portfolio.pedidos.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    public static final String TOPIC_PEDIDOS = "pedidos-events";

    @Bean
    public NewTopic pedidosEvents() {
        return TopicBuilder.name(TOPIC_PEDIDOS)
                .partitions(3)
                .replicas(1)
                .build();
    }
}