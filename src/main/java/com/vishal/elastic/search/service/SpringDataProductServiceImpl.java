package com.vishal.elastic.search.service;

import com.vishal.elastic.search.model.Product;
import com.vishal.elastic.search.model.ProductResponse;
import com.vishal.elastic.search.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SpringDataProductServiceImpl implements ISpringDataProductService {

    private final ProductRepository productRepository;

    @Autowired
    public SpringDataProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(final Product product) {
        return productRepository.save(product);
    }

    @Override
    public Iterable<Product> insertBulkProducts(final List<Product> products) {
        return productRepository.saveAll(products);
    }

    @Override
    public Optional<Product> getProduct(final String id) {
        return productRepository.findById(id);
    }

    @Override
    public Boolean deleteProduct(final String id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception ex) {
            log.error("Error in deleting the product for product id: {}", id, ex);
            return false;
        }
        return true;
    }

    @Override
    public ProductResponse getProductsByName(final String name) {
        List<Product> productList = productRepository.findByName(name);
        return new ProductResponse(productList);
    }

    @Override
    public ProductResponse getProductByNameUsingAnnotation(final String name) {
        List<Product> productList =  productRepository.findAllByNameUsingAnnotations(name);
        return new ProductResponse(productList);
    }

    @Override
    public ProductResponse getProductByManufacturerAndCategory(final String manufacturer, final String category) {
        List<Product> productList =  productRepository.findByManufacturerAndCategory(manufacturer, category);
        return new ProductResponse(productList);
    }

    @Override
    public ProductResponse getProductByMatchingNames(final String name) {
        List<Product> productList =  productRepository.findByNameContaining(name);
        return new ProductResponse(productList);
    }
}
