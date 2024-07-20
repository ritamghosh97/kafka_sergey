package com.appsdeveloperblog.ws.products.service;

import com.appsdeveloperblog.ws.products.entity.CreateProductRestModel;

import java.util.concurrent.ExecutionException;

public interface ProductService {
    String createProduct(CreateProductRestModel productRestModel) throws ExecutionException, InterruptedException;
}
