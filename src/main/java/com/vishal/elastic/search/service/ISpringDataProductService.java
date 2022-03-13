package com.vishal.elastic.search.service;

import com.vishal.elastic.search.model.Product;
import com.vishal.elastic.search.model.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ISpringDataProductService {

    Product createProduct(final Product product);

    Iterable<Product> insertBulkProducts(final List<Product> products);

    Optional<Product> getProduct(final String id);

    Boolean deleteProduct(final String id);

    ProductResponse getProductsByName(final String name);

    ProductResponse getProductByNameUsingAnnotation(final String name);

    ProductResponse getProductByManufacturerAndCategory(final String manufacturer, final String category);

    ProductResponse getProductByMatchingNames(final String name);


}
