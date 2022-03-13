package com.vishal.elastic.search.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class ProductResponse {

    List<Product> productList;

    public ProductResponse(List<Product> productList) {
        this.productList = productList;
    }
}
