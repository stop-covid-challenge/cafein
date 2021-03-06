package stop.covid.challenge.cafein.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "following")
@Getter
@Setter
@NoArgsConstructor
public class Following extends BaseTimeEntity {
    @Id @Column(name = "following_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 내 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 내가 팔로잉하는 사람의 아이디
    @Column(name = "follower_id")
    private Long follower_id;

    public void addFollowing(User user) {
        this.user = user;
    }
}
