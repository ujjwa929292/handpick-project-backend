package com.handpick.thehandpickapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.handpick.thehandpickapp.accessors.ProductRepository;
import com.handpick.thehandpickapp.accessors.models.Product;
import com.handpick.thehandpickapp.models.FileCreateDTO;
import com.handpick.thehandpickapp.utils.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.handpick.thehandpickapp.accessors.FileRepository;
import com.handpick.thehandpickapp.accessors.models.File;
import com.handpick.thehandpickapp.constants.Constants;
import com.handpick.thehandpickapp.models.FileDTO;
import com.handpick.thehandpickapp.service.FileService;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<FileDTO> getAllTapes() {
        List<File> tapes = fileRepository.getAllTapes(Constants.FILE_TYPE_VIDEO);
        return tapes.stream()
                .map(file -> modelMapper.map(file, FileDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public File createFile(FileCreateDTO fileDTO) {
        Product product = productRepository.findById(fileDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        File file = modelMapper.map(fileDTO, File.class);
        file.setId(null);
        File createdFile = fileRepository.save(file);
        List<UUID> files;
        if (product.getFileIds() != null) {
            files = product.getFileIds();
        } else {
            files = new ArrayList<>();
        }
        files.add(createdFile.getId());
        product.setFileIds(files);
        productRepository.save(product);
        return createdFile;
    }

}