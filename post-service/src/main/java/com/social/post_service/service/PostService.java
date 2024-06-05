package com.social.post_service.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    void uploadPost(List<MultipartFile> files, String userName) throws IOException;

    List<List<String>> getAllPostByUserName(String userName);
}
