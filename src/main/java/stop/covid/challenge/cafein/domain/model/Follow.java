package stop.covid.challenge.cafein.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stop.covid.challenge.cafein.domain.type.Subscription;

import javax.persistence.*;

@Entity(name = "follow")
@Getter
@Setter
@NoArgsConstructor
public class Follow {
    @Id @Column(name = "follow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private PersonalCafe follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private PersonalCafe following;

    @Enumerated(EnumType.STRING)
    private Subscription subscription;
}
