package com.social.post_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "post_url")
    @Lob
    private String postUrl;

    @Column(name = "user_name")
    private String userName;

    private String listOfTags;

    @Column(name = "description")
    @Lob
    private String description;

    @Column(name = "like_count")
    private Integer likeCount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<PostComment> comments;
}
