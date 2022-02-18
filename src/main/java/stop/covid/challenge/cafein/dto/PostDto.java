package stop.covid.challenge.cafein.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import stop.covid.challenge.cafein.domain.model.Comment;
import stop.covid.challenge.cafein.domain.model.HashTag;
import stop.covid.challenge.cafein.domain.model.Image;
import stop.covid.challenge.cafein.domain.model.Post;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostDto {

    private String writing;
    private Integer like;
    private List<HashTag> hashTags = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<Image> images = new ArrayList<>();

    public Post toEntity() {
        return Post.builder()
            .writing(writing)
            .like(like)
            .hashTags(hashTags)
            .comments(comments)
            .images(images)
            .build();
    }
}
