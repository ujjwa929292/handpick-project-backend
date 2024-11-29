package com.handpick.thehandpickapp.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.handpick.thehandpickapp.accessors.FileRepository;
import com.handpick.thehandpickapp.accessors.ProductRepository;
import com.handpick.thehandpickapp.accessors.models.File;
import com.handpick.thehandpickapp.accessors.models.Product;
import com.handpick.thehandpickapp.models.FileDTO;
import com.handpick.thehandpickapp.models.ProductCreateDTO;
import com.handpick.thehandpickapp.models.ProductDTO;
import com.handpick.thehandpickapp.models.ProductRestrictedDTO;
import com.handpick.thehandpickapp.models.ReviewCreateDTO;
import com.handpick.thehandpickapp.models.ReviewDTO;
import com.handpick.thehandpickapp.service.ProductService;
import com.handpick.thehandpickapp.utils.ResourceNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ReviewServiceImpl reviewServiceImpl;

    @Transactional
    @Override
    public ProductDTO createProduct(ProductCreateDTO productDTO) {

        var product = modelMapper.map(productDTO, Product.class);
        product.setId(null); // TODO: Fix why mapper not working correctly

        // TODO: Make this an upsert instead of an insert
        
        // Handle Files: Convert array of FileDTO to List and collect IDs
        if (productDTO.getFiles() != null) {
            List<UUID> fileIds = Arrays.stream(productDTO.getFiles()) // Convert to stream
                    .map(fileDTO -> {
                        File file = modelMapper.map(fileDTO, File.class);
                        File insertedFile = fileRepository.save(file); // Save the file entity
                        return insertedFile.getId(); // Collect the ID
                    })
                    .collect(Collectors.toList()); // Collect to list
            product.setFileIds(fileIds); // Set the list of file IDs in the Product
        }

        var savedProduct = productRepository.save(product);

        // Optionally map back to ProductDTO if needed
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Transactional
    @Override
    public Optional<ProductDTO> getProductById(UUID id) {

        return productRepository.findById(id)
                .map(product -> {
                    ProductDTO dto = modelMapper.map(product, ProductDTO.class);

                    List<FileDTO> files = fileRepository.findAllById(product.getFileIds())
                            .stream()
                            .map(file -> modelMapper.map(file, FileDTO.class))
                            .collect(Collectors.toList());
                    dto.setFiles(files);
                    return dto;
                });
    }

    @Override
    public List<ProductRestrictedDTO> getAllProducts(List<String> sort) {

        Sort sortOrder = Sort.unsorted();
        if (sort != null && !sort.isEmpty()) {
            for (String sortParam : sort) {
                String[] sortDetails = sortParam.split(":");
                String field = sortDetails[0];
                String direction = sortDetails.length > 1 ? sortDetails[1] : "asc";

                Sort.Order order = new Sort.Order(Sort.Direction.fromString(direction), field);
                sortOrder = sortOrder.and(Sort.by(order));
            }
        }
        List<Product> products = productRepository.findAll(sortOrder);
        return products.stream()
                .map(product -> modelMapper.map(product, ProductRestrictedDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ProductDTO updateProduct(UUID id, ProductCreateDTO productDTO) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new RuntimeException("Product not found with ID: " + id);
        }

        Product product = optionalProduct.get();
        modelMapper.map(productDTO, product);
        product.setId(id);

        if (productDTO.getFiles() != null) {
            List<UUID> fileIds = Arrays.stream(productDTO.getFiles())
                    .map(fileDTO -> {
                        File file = modelMapper.map(fileDTO, File.class);
                        File insertedFile = fileRepository.save(file);
                        return insertedFile.getId();
                    })
                    .collect(Collectors.toList());
            product.setFileIds(fileIds);
        }

        entityManager.clear();
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    // TODO: Should we delete files and tags associated along with product
    @Override
    public void deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    // @Override
    // public List<ProductDTO> getProductsByTagId(UUID tagId) {
    // return productRepository.findProductsByTagId(tagId).stream()
    // .map(modelMapper::toDto)
    // .collect(Collectors.toList());
    // }

    @Override
    public List<FileDTO> getFilesByProductId(UUID productId) {
        return null;

        // fileRepository.findFilesByProductId(productId).stream()
        // .map(fileMapper::toDto)
        // .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getReviewsByProductId(UUID productId) {
        return reviewServiceImpl.getReviewsByProductId(productId);
    }

    @Override
    public void deleteReviewsByProduct(UUID productId) {
        reviewServiceImpl.deleteReviewsByProductId(productId);
    }

    @Override
    public List<ReviewDTO> createReviewsByProduct(UUID productId, List<ReviewCreateDTO> reviewCreateDTOs) {
        return reviewServiceImpl.bulkCreateReviewsbyProductId(productId, reviewCreateDTOs);
    }

    @Override
    public List<ProductRestrictedDTO> searchProducts(String searchTerm) {

        List<Product> products = productRepository.searchByFullText(searchTerm);
        return products.stream()
                .map(product -> modelMapper.map(product, ProductRestrictedDTO.class))
                .collect(Collectors.toList());
    }

}