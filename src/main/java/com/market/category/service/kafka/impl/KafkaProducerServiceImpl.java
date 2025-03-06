package com.market.category.service.kafka.impl;

import com.market.category.service.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final String categoryChanged;

    private final String categoryAttributeChanged;

    public KafkaProducerServiceImpl(KafkaTemplate<String, String> kafkaTemplate,
                                    @Value("${spring.kafka.topics.category-changed}")
                                    String categoryChanged,
                                    @Value("${spring.kafka.topics.category-attribute-changed}")
                                    String categoryAttributeChanged) {
        this.kafkaTemplate = kafkaTemplate;
        this.categoryChanged = categoryChanged;
        this.categoryAttributeChanged = categoryAttributeChanged;
    }

    @Override
    public void sendCategoryChangedMessage(String message) {
        kafkaTemplate.send(categoryChanged, message);
    }

    @Override
    public void sendCategoryAttributeChangedMessage(String message) {
        kafkaTemplate.send(categoryAttributeChanged, message);
    }
}
