package com.social.post_service.file;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileService {
    Mono<String> save(String uploadDir, MultipartFile file, String fileName) throws IOException;

    Path getFolderPath(String location);
    byte[] getResource(Path path,String fileName) throws IOException;

    List<String> getPostUrls(String path);
}
