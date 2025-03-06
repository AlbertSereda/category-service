package com.market.category.service.kafka;

public interface KafkaProducerService {

    void sendCategoryChangedMessage(String message);

    void sendCategoryAttributeChangedMessage(String message);
}
