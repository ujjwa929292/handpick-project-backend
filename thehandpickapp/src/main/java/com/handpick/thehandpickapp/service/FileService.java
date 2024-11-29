package com.handpick.thehandpickapp.service;

import java.util.List;

import com.handpick.thehandpickapp.accessors.models.File;
import com.handpick.thehandpickapp.models.FileCreateDTO;
import com.handpick.thehandpickapp.models.FileDTO;

public interface FileService {
    List<FileDTO> getAllTapes();
    File createFile(FileCreateDTO file);
}