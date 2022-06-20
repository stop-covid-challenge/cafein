package stop.covid.challenge.cafein.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String socialId;
    private String nickname;
    private String profileImage;
    private String phoneNumber;

    //회원 등록
    public static User createUser(String socialId, String email, String nickname, String profileImage) {
        User user = new User();
        user.setSocialId(socialId);
        user.setEmail(email);
        user.setNickname(nickname);
        user.setProfileImage(profileImage);
        return user;
    }
}
