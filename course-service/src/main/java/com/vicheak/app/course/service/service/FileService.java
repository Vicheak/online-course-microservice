package com.vicheak.app.course.service.service;

import com.vicheak.app.course.service.dto.FileDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * This method is used to upload single file image (restricted extensions)
     * @param file is the request part from client
     * @return FileDto
     */
    FileDto uploadSingleRestrictImage(MultipartFile file);

    /**
     * This method is used to download specific file by name
     * @param name is the path parameter from client
     * @return Resource
     */
    Resource downloadByName(String name);

}
