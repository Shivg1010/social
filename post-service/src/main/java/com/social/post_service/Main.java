package com.social.post_service;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Log4j2
public class Main {
    public static void main(String[] args) {
        String uploadDir = "post/users/userName1/post";
        Path path = getFolderPath(uploadDir);

        System.out.println(path.getFileSystem());
    }

    public static Path getFolderPath(String location)  {
        String currentDir = System.getProperty("user.dir");
        //creating dir if not exists
        File newDir = new File(currentDir, location);
        log.info(newDir.getAbsolutePath());
        if (!newDir.exists()) {
            boolean isCreated = newDir.mkdirs();
            if (isCreated) {
                log.info("Directory created: " + newDir.getAbsolutePath());
            } else {
                log.info("Failed to create directory: " + newDir.getAbsolutePath());
            }
        }

        return newDir.toPath();
    }

    public byte[] getResource(Path path,String fileName) throws IOException {
        return Files.readAllBytes(path.resolve(fileName));
    }

    private String getFilenameWithExtension(String newName, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        assert originalFilename != null;
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = originalFilename.substring(dotIndex);
        }
        return newName + extension;
    }
}
