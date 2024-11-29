package com.handpick.thehandpickapp.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.handpick.thehandpickapp.constants.UrlConstants;
import com.handpick.thehandpickapp.models.ProductCreateDTO;
import com.handpick.thehandpickapp.models.ProductDTO;
import com.handpick.thehandpickapp.models.ProductRestrictedDTO;
import com.handpick.thehandpickapp.models.ReviewCreateDTO;
import com.handpick.thehandpickapp.models.ReviewDTO;
import com.handpick.thehandpickapp.service.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping(UrlConstants.PRODUCT_API_BASE_PATH)
@Tag(name = "Product", description = "Operations related to products")
@CrossOrigin(maxAge = 3600)
@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    // Create a new Product
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductCreateDTO productCreateDTO) {
        ProductDTO createdProduct = productService.createProduct(productCreateDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID id, @RequestBody ProductCreateDTO productDetails) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete a Product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // Get a Product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID id) {
        Optional<ProductDTO> product = productService.getProductById(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK); // If product is found
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // If product is not found
        }
    }

    // Get all Products
    @GetMapping
    public ResponseEntity<List<ProductRestrictedDTO>> getAllProducts(@RequestParam(value = "sort", required = false) List<String> sort) {
        List<ProductRestrictedDTO> products = productService.getAllProducts(sort);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<List<ProductRestrictedDTO>> searchProducts(@PathVariable String searchTerm) {
        List<ProductRestrictedDTO> products = productService.searchProducts(searchTerm);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviews(@PathVariable UUID id) {
        List<ReviewDTO> reviews = productService.getReviewsByProductId(id);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> postReviews(@PathVariable UUID id , @RequestBody List<ReviewCreateDTO> reviewCreateDTOs) {
        List<ReviewDTO> reviews = productService.createReviewsByProduct(id, reviewCreateDTOs);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> deleteReviews(@PathVariable UUID id) {
        productService.deleteReviewsByProduct(id);
        return ResponseEntity.noContent().build();
    }
}
