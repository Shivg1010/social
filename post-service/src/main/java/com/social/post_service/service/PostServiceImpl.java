package com.social.post_service.service;

import com.social.post_service.entity.Post;
import com.social.post_service.entity.dto.PostDto;
import com.social.post_service.entity.dto.PostResponse;
import com.social.post_service.file.FileService;
import com.social.post_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostServiceImpl implements PostService {

    @Value("${file.upload.location}")
    private String postServerLocation;
    private final FileService fileService;

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    public void uploadPost(List<MultipartFile> files, String userName) throws IOException {
        String path = postServerLocation + "/" + userName + "/" + System.currentTimeMillis();
        Mono<List<String>> uploadedFilesNames = Flux.fromIterable(files).flatMap(file -> {
            try {
                return fileService.save(path, file, file.getName()).doOnError(e -> System.err.println("Error uploading file: " + file.getOriginalFilename())).onErrorResume(e -> Mono.empty());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collectList()
                .map(uploadedFiles -> {
            if (uploadedFiles.size() == files.size()) {
                log.error("All files uploaded successfully");
            } else {
                log.error("Some files failed to upload. Successfully uploaded files: " + uploadedFiles);
            }
            return uploadedFiles;
        });
    }

    public PostResponse savePost(PostDto postDto){
        Post post = new Post();
        modelMapper.map(postDto, post);

        Post savedPost = postRepository.save(post);
        PostResponse postResponse = new PostResponse();
        modelMapper.map(savedPost, postResponse);
        return postResponse;
    }

    @Override
    public List<List<String>> getAllPostByUserName(String userName) {
        List<Post> postList = postRepository.findByUserName(userName);
        List<List<String>> postUrls = new LinkedList<>();

        for(Post post : postList){
            String postUrl = post.getPostUrl();
            List<String> fileNames = fileService.getPostUrls(postUrl);

            postUrls.add(fileNames);
        }

        return postUrls;
    }
}
