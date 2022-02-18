package stop.covid.challenge.cafein.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String kakaoId;
    private String phoneNumber;

    //cafe 연관관계 OneToMany와 메소드 설정
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PersonalCafe> personalCafes = new ArrayList<>();

}
