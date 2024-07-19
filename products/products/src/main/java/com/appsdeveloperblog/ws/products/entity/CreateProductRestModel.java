package com.appsdeveloperblog.ws.products.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CreateProductRestModel {

    private String title;
    private BigDecimal price;
    private Integer quantity;

}
