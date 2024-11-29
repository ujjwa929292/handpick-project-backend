package com.handpick.thehandpickapp.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.handpick.thehandpickapp.models.FileDTO;
import com.handpick.thehandpickapp.models.ProductCreateDTO;
import com.handpick.thehandpickapp.models.ProductDTO;
import com.handpick.thehandpickapp.models.ProductRestrictedDTO;
import com.handpick.thehandpickapp.models.ReviewCreateDTO;
import com.handpick.thehandpickapp.models.ReviewDTO;

public interface ProductService {
    ProductDTO createProduct(ProductCreateDTO productDTO);
    Optional<ProductDTO> getProductById(UUID id);
    List<ProductRestrictedDTO> getAllProducts(List<String> sort);
    ProductDTO updateProduct(UUID id, ProductCreateDTO productDTO);
    void deleteProduct(UUID id);
    List<FileDTO> getFilesByProductId(UUID productId);
    List<ReviewDTO> getReviewsByProductId(UUID productId);
    void deleteReviewsByProduct(UUID productId);
    List<ReviewDTO> createReviewsByProduct(UUID productId, List<ReviewCreateDTO> reviewCreateDTOs);
    List<ProductRestrictedDTO> searchProducts(String searchTerm);
}