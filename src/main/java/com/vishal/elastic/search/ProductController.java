package com.vishal.elastic.search;


import com.vishal.elastic.search.model.Product;
import com.vishal.elastic.search.model.ProductResponse;
import com.vishal.elastic.search.service.ISpringDataProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    private final ISpringDataProductService productService;

    @Autowired
    public ProductController(ISpringDataProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody final Product product) {
        Product response;
        try {
            response = productService.createProduct(product);
        } catch (Exception ex) {
            log.error("Error while creating product", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/_bulk")
    public ResponseEntity<List<Product>> createMultipleProduct(@RequestBody final List<Product> products) {
        List<Product> response;
        try {
            response = (List<Product>) productService.insertBulkProducts(products);
        } catch (Exception ex) {
            log.error("Error while inserting bulk products in elastic search index", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable final String id) {
        Product response = null;
        try {
            Optional<Product> optionalProduct = productService.getProduct(id);
            if (!optionalProduct.isPresent()) {
                log.info("No product for this id: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            response = optionalProduct.get();
        } catch (Exception ex) {
            log.error("Error while fetching product for id: {}", id, ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable final String id) {
        Boolean response;
        try {
            response = productService.deleteProduct(id);
        } catch (Exception ex) {
            log.error("Error in deleting the product for product id: {}", id, ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    @ResponseBody
    public ResponseEntity<ProductResponse> getProductByName(@PathVariable final String name) {
        ProductResponse response;
        try {
            response = productService.getProductsByName(name);
        } catch (Exception ex) {
            log.error("Error in fetching the product for product name: {}", name, ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("manufacturer/{manufacturer}/category/{category}")
    public ResponseEntity<ProductResponse> getProductByManufacturerAndCategory(@PathVariable final String manufacturer, @PathVariable final String category){
        ProductResponse response;
        try{
            response = productService.getProductByManufacturerAndCategory(manufacturer, category);
        } catch (Exception ex) {
            log.error("Error in fetching the product for product name: {} and category: {}", manufacturer, category, ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("name/{name}/annotations")
    public ResponseEntity<ProductResponse> getProductByNameUsingAnnotation(@PathVariable final String name){
        ProductResponse response;
        try{
            response = productService.getProductByNameUsingAnnotation(name);
        } catch (Exception ex) {
            log.error("Error in fetching the product for product name: {}", name, ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/matching/{name}")
    public ResponseEntity<ProductResponse> getProductByMatchingName(@PathVariable final String name){
        ProductResponse response;
        try{
            response = productService.getProductByMatchingNames(name);
        } catch (Exception ex) {
            log.error("Error in fetching the product for product name: {}", name, ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
