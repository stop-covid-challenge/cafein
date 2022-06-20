package stop.covid.challenge.cafein.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "follower")
@Getter
@Setter
@NoArgsConstructor
public class Follower extends BaseTimeEntity {
    @Id @Column(name = "follower_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 내 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 나를 팔로우하는 사람의 고유 아이디
    @Column(name = "following_id")
    private Long following_id;

    public void addFollower(User user) {
        this.user = user;
    }

}
