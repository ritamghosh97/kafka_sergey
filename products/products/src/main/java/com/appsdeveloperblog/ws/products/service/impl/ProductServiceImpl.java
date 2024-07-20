package com.appsdeveloperblog.ws.products.service.impl;

import com.appsdeveloperblog.ws.products.entity.CreateProductRestModel;
import com.appsdeveloperblog.ws.products.kafka.event.ProductCreatedEvent;
import com.appsdeveloperblog.ws.products.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class ProductServiceImpl implements ProductService {

    private KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductRestModel productRestModel)
                                                throws ExecutionException, InterruptedException {
        String productId = UUID.randomUUID().toString();

        // TODO: Persist product details into database before publishing an Event

        ProductCreatedEvent productCreatedEvent
                = new ProductCreatedEvent(productId,
                                        productRestModel.getTitle(),
                                        productRestModel.getPrice(),
                                        productRestModel.getQuantity());

        logger.info("***** Before publishing the ProductCreatedEvent...");

        SendResult<String, ProductCreatedEvent> result = kafkaTemplate
                .send("product-created-events-topic", productId, productCreatedEvent).get();

        logger.info("Topic Name: {}", result.getRecordMetadata().topic());
        logger.info("Partition Number: {}", result.getRecordMetadata().partition());
        logger.info("Partition Offset: {}", result.getRecordMetadata().offset());
        logger.info("Timestamp of the message stored: {}", new Date(result.getRecordMetadata().timestamp()));

        logger.info("****** Returning product id: {}", productId);

        return productId;
    }
}
