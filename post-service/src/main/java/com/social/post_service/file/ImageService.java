package com.social.post_service.file;

import lombok.extern.log4j.Log4j2;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
public class ImageService implements FileService {
    @Override
    @Async
    public Mono<String> save(String uploadDir, MultipartFile file, String fileName) throws IOException {
        Path path = getFolderPath(uploadDir);
        String imageName = getFilenameWithExtension(fileName, file);
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, path);
        }
//        Files.copy(file.getInputStream(), path.resolve(imageName));
        return Mono.just(imageName);
    }

    @Override
    public Path getFolderPath(String location) {
        String currentDir = System.getProperty("user.dir");
        //creating dir if not exists
        File newDir = new File(currentDir, location);
        if (!newDir.exists()) {
            boolean isCreated = newDir.mkdir();
            if (isCreated) {
                log.info("Directory created: " + newDir.getAbsolutePath());
            } else {
                log.info("Failed to create directory: " + newDir.getAbsolutePath());
            }
        }

        return newDir.toPath();
    }

    @Override
    public byte[] getResource(Path path, String fileName) throws IOException {
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

    public List<String> getPostUrls(String path){
        String currentDir = System.getProperty("user.dir");
        File newDir = new File(currentDir, path);
        if (!newDir.exists()) {
            log.info("Unable to get post of this url: " + path);
            return null;
        }

        return Arrays.stream(newDir.list()).map( (fileName)-> fileName+path ).toList();
    }

}
