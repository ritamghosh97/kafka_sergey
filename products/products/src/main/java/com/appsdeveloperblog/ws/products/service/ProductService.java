package com.appsdeveloperblog.ws.products.service;

import com.appsdeveloperblog.ws.products.entity.CreateProductRestModel;

public interface ProductService {
    String createProduct(CreateProductRestModel productRestModel);
}
