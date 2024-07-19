package com.mymart;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadUtil {

	public static final String UPLOAD_DIR = "src/main/resources/static/uploaded_images";
	 
	 public static void saveFile(String uploadDir, String fileName, byte[] fileBytes) throws IOException {
	        Path uploadPath = Paths.get(uploadDir);

	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }

	        Path filePath = uploadPath.resolve(fileName);
	        Files.write(filePath, fileBytes);
	        
	    }
	}