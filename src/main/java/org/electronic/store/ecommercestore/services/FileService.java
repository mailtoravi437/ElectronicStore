package org.electronic.store.ecommercestore.services;

import org.electronic.store.ecommercestore.dtos.ImageResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public interface FileService {
    public String uploadFile(MultipartFile file,String path);
    InputStream getResource(String path,String fileName) throws IOException;

    String uploadUserImage(MultipartFile file, String userId);
}
