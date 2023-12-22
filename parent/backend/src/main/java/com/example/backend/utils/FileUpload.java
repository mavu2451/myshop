package com.example.backend.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUpload {
    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try(InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new IOException("Không thể thêm file:" + fileName, e);
        }
    }
    public static void clear(String dir){
        Path path = Paths.get(dir);
        try{
            Files.list(path).forEach(file->{
                if(!Files.isDirectory(file)){
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        throw new RuntimeException("Không thể xoá " + e);
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Không thể xoá: " + e);
        }
    }
}
