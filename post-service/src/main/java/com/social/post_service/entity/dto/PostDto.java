package com.social.post_service.entity.dto;

import lombok.Data;

@Data
public class PostDto {
    private String userName;
    private String listOfTags;
    private String description;
    private Integer likeCount;
}
