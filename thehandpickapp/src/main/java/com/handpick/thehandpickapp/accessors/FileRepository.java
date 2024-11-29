package com.handpick.thehandpickapp.accessors;

import java.util.UUID;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.handpick.thehandpickapp.accessors.models.File;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {

    @Query(value = "select * from file where file_type = ?1", nativeQuery = true)
    List<File> getAllTapes(String type);

}