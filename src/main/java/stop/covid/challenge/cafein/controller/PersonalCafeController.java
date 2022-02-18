package stop.covid.challenge.cafein.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import stop.covid.challenge.cafein.domain.model.Cafe;
import stop.covid.challenge.cafein.domain.model.PersonalCafe;
import stop.covid.challenge.cafein.domain.model.User;
import stop.covid.challenge.cafein.repository.PersonalCafeRepository;
import stop.covid.challenge.cafein.repository.UserRepository;

@RestController
@RequestMapping("/personal")
@RequiredArgsConstructor
public class PersonalCafeController {

    private final PersonalCafeRepository personalCafeRepository;
    private final UserRepository userRepository;

    // 프로필 등록
    @PostMapping(value = "/register")
    public ResponseEntity<PersonalCafe> registerInfo(@RequestBody Cafe cafe) {
        PersonalCafe personalCafe = new PersonalCafe();
        personalCafe.setNickname(cafe.getNickname());
        personalCafe.setIntroduce(cafe.getIntroduce());
        personalCafe.setAddress(cafe.getAddress());
        personalCafe.setProfileImage(cafe.getProfileImage());
        personalCafe.setBackgroundImage(cafe.getBackgroundImage());

        // User 생성 및 더하기
        User user = new User();
        personalCafe.addUser(user);

        // PersonalCafe 생성 및 저장
        personalCafeRepository.save(personalCafe);

        return ResponseEntity.ok(personalCafe);
    }

    // 프로필 조회 할 때 (내 포스트 전체까지 볼 수 있다.)
    @GetMapping(value = "/profile")
    public ResponseEntity<PersonalCafe> getInfo(
        @RequestParam(value = "kakaoId") String kakaoId,
        @RequestParam(value = "nickname") String nickname
    ) throws HttpClientErrorException.NotFound {

        // 카카오 아이디로 조회
        User user = userRepository.findUserByKakaoId(kakaoId);

        for (PersonalCafe personalCafe : user.getPersonalCafes()) {
            if (personalCafe.getNickname() == nickname) {
                return ResponseEntity.ok(personalCafe);
            }
        }
        return null;

    }

}
