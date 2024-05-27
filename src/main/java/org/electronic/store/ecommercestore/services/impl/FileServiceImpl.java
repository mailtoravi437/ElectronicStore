package org.electronic.store.ecommercestore.services.impl;

import org.electronic.store.ecommercestore.dtos.ImageResponse;
import org.electronic.store.ecommercestore.exceptions.BadRApiRequest;
import org.electronic.store.ecommercestore.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadFile(MultipartFile file, String path) {
        String originalFilename = file.getOriginalFilename();
        logger.info("Filename -: {}",originalFilename);
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = filename + extension;
        String fullPathWithFileName = path+ File.separator+fileNameWithExtension;

        if(extension.equals(".jpg") || extension.equals(".jpeg") || extension.equals(".png") || extension.equals(".gif")){
            try {
                File folder = new File(path);
                if(!folder.exists()) {
                    folder.mkdirs();
                }
                Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
                return fileNameWithExtension;
            } catch (Exception e) {
                logger.error("Error uploading file",e);
                return "";
            }
        }
        else{
            throw new BadRApiRequest("File with this extension not allowed" + extension + " allowed extensions are jpg,jpeg,png,gif");
        }

    }

    @Override
    public InputStream getResource(String path, String fileName) throws IOException {
        String fullPathWithFileName = path + File.separator + fileName;
        try {
            return Files.newInputStream(Paths.get(fullPathWithFileName));
        } catch (IOException e) {
            logger.error("Error getting file", e);
            throw new IOException("Error getting file", e);
        }
    }

    @Override
    public String uploadUserImage(MultipartFile file, String userId) {
        // Generate a unique filename using UUID
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = UUID.randomUUID().toString() + extension;

        // Define the path where the file will be stored
        String fullPathWithFileName = imageUploadPath + File.separator + filename;

        // Check if the file's extension is allowed
        if(extension.equals(".jpg") || extension.equals(".jpeg") || extension.equals(".png") || extension.equals(".gif") || extension.equals(".svg")){
            try {
                // Create the directory if it doesn't exist
                File folder = new File(imageUploadPath);
                if(!folder.exists()) {
                    folder.mkdirs();
                }

                // Save the file
                Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));

                // Return the filename
                return filename;
            } catch (Exception e) {
                logger.error("Error uploading file", e);
                return "";
            }
        } else {
            throw new BadRApiRequest("File with this extension not allowed" + extension + " allowed extensions are jpg,jpeg,png,gif");
        }
    }
}
