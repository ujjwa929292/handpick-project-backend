package com.handpick.thehandpickapp.controllers;

import java.util.List;

import com.handpick.thehandpickapp.accessors.models.File;
import com.handpick.thehandpickapp.models.FileCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.handpick.thehandpickapp.constants.UrlConstants;
import com.handpick.thehandpickapp.models.FileDTO;
import com.handpick.thehandpickapp.service.FileService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping(UrlConstants.FILE_API_BASE_PATH)
@Tag(name = "File", description = "Operations related to files")
@CrossOrigin(maxAge = 3600)
@Validated
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    // Get all Videos
    @GetMapping("/tapes")
    public ResponseEntity<List<FileDTO>> getAllTapes() {
        List<FileDTO> tapes = fileService.getAllTapes();
        return ResponseEntity.ok(tapes);
    }

    // Get all Videos
    @PostMapping
    public ResponseEntity<File> createFile(@RequestBody FileCreateDTO fileDTO) {
        File createdFile = fileService.createFile(fileDTO);

        return new ResponseEntity<>(createdFile, HttpStatus.CREATED);
    }

}
