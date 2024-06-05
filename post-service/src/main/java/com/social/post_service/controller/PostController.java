package com.social.post_service.controller;

import com.social.post_service.entity.dto.PostDto;
import com.social.post_service.response.ApiResponse;
import com.social.post_service.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    public ApiResponse<String> addPost(
            @RequestPart("post") PostDto postDto,
            @RequestParam("post_content") List<MultipartFile> postContent
    ) throws IOException {
        postService.uploadPost(postContent, postDto.getUserName());

        return new ApiResponse<String>(true, Map.of("postResponse",""), "Post Created Successfully");
    }

    public ApiResponse<List<List<String>>> getPost(String userName){
        return new ApiResponse<>( true, Map.of("allPost", postService.getAllPostByUserName(userName)), "Get All Post" );
    }
}
