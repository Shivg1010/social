import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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

    @Length
    @org.hibernate.validator.constraints.URL
    @Column(name = "post_url")
    @Lob
    private String postUrl;

    @Column(name = "user_id")
    private Integer userId;

    private String listOfTags;

    @Column(name = "description")
    @Lob
    private String description;

    @PositiveOrZero
    @Column(name = "like_count")
    private Integer likeCount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments;

}
