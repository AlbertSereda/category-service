package com.market.category.service.kafka.impl;

import com.market.category.service.kafka.KafkaProducerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaProducerServiceImplTest {

    private static final String CATEGORY_CHANGED_TOPIC = "category_changed";

    private static final String CATEGORY_ATTRIBUTE_CHANGED_TOPIC = "category_attribute_changed";

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    private KafkaProducerService kafkaProducerService;

    @BeforeEach
    void setUp() {
        kafkaProducerService = new KafkaProducerServiceImpl(kafkaTemplate,
                                                            CATEGORY_CHANGED_TOPIC,
                                                            CATEGORY_ATTRIBUTE_CHANGED_TOPIC);
    }

    @Test
    void testSendCategoryChangedMessage() {
        String message = "Test category changed message";

        kafkaProducerService.sendCategoryChangedMessage(message);

        verify(kafkaTemplate, times(1)).send(eq(CATEGORY_CHANGED_TOPIC), eq(message));
    }

    @Test
    void testSendCategoryAttributeChangedMessage() {
        String message = "Test category attribute changed message";

        kafkaProducerService.sendCategoryAttributeChangedMessage(message);

        verify(kafkaTemplate, times(1)).send(eq(CATEGORY_ATTRIBUTE_CHANGED_TOPIC), eq(message));
    }
}