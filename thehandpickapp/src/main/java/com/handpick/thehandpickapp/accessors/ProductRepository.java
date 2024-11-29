package com.handpick.thehandpickapp.accessors;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.handpick.thehandpickapp.accessors.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAll(Sort sort);

    @Query(value = "SELECT * FROM Product WHERE search_vector @@ plainto_tsquery('english', :searchTerm)", nativeQuery = true)
    List<Product> searchByFullText(@Param("searchTerm") String searchTerm);
}