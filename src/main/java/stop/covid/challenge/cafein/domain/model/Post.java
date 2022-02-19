package stop.covid.challenge.cafein.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String writing;
    private int likeNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_cafe_id")
    private PersonalCafe personalCafe;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<HashTag> hashTags = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    public void setPersonalCafe(PersonalCafe personalCafe) {
        this.personalCafe = personalCafe;
        personalCafe.getPosts().add(this);
    }
}
