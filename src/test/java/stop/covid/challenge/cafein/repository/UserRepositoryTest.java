package stop.covid.challenge.cafein.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import stop.covid.challenge.cafein.domain.model.User;

import java.util.List;

public class UserRepositoryTest extends RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자가 DB에 잘 저장되는지 확인")
    void saveUser() {
        //given
        String email = "jinsung1048@gmail.com";
        String nickname = "good";
        String socialId = "123456";
        String profileImage = "프로필 이미지";
        String phoneNumber = "010-1234-1234";
        final User user = User.builder()
            .email(email)
            .socialId(socialId)
            .nickname(nickname)
            .profileImage(profileImage)
            .phoneNumber(phoneNumber)
            .build();

        // when
        final User savedUser = userRepository.save(user);

        // then
        Assertions.assertEquals(user, savedUser);
    }

    @Test
    @DisplayName("저장된 유저가 잘 조회되는지 확인")
    void findUser() {
        // given
        User user1 = User.createUser("1", "wlstjd1048@naver.com", "구리", "이미지");
        User user2 = User.createUser("2", "jinsung1048@gmail.com", "지메일", "프로필");
        userRepository.saveAll(List.of(user1, user2));

        // when
        User findUser1 = userRepository.findUserBySocialId("1");
        User findUser2 = userRepository.findUserBySocialId("2");

        // then
        Assertions.assertEquals(user1.getEmail(), findUser1.getEmail());
        Assertions.assertEquals(user2.getEmail(), findUser2.getEmail());
        Assertions.assertEquals(user1.getNickname(), findUser1.getNickname());
        Assertions.assertEquals(user2.getNickname(), findUser2.getNickname());

    }
}
