package stop.covid.challenge.cafein.service;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stop.covid.challenge.cafein.domain.auth.AuthorizationKakao;
import stop.covid.challenge.cafein.domain.model.User;
import stop.covid.challenge.cafein.dto.UserDto;
import stop.covid.challenge.cafein.repository.UserRepository;
import stop.covid.challenge.cafein.service.auth.Oauth2Kakao;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final Oauth2Kakao oauth2Kakao;

    public User getUserInfo(Long id) {
        User user = userRepository.findById(id).get();
        return user;
    }

    @Transactional
    public User loginRegister(UserDto userDto) {
        User user = new User();
        if (!userDto.getNickname().isEmpty()) {
            user.setNickname(userDto.getNickname());
        }
        if (!userDto.getSocialId().isEmpty()) user.setSocialId(userDto.getSocialId());
        if (!userDto.getProfileImage().isEmpty()) {
            user.setProfileImage(userDto.getProfileImage());
        }
        if (!userDto.getEmail().isEmpty()) user.setEmail(userDto.getEmail());

        if (!userDto.getIntroduce().isEmpty()) user.setIntroduce(userDto.getIntroduce());
        if (!userDto.getAddress().isEmpty()) user.setAddress(userDto.getAddress());
        if (!userDto.getBackgroundImage().isEmpty()) user.setBackgroundImage(userDto.getBackgroundImage());

        User findUser = userRepository.findUserBySocialId(user.getSocialId());
        User a = null;
        if (findUser != a) {
            return findUser;
        } else {
            return userRepository.save(user);
        }
    }

    // 웹에서 카카오 로그인을 구현할 경우
    public ResponseEntity<User> oauth2AuthorizationKakao(String code) {
        AuthorizationKakao authorization = oauth2Kakao.callTokenApi(code);
        System.out.println(authorization.getAccess_token());
        String userInfoFromKakao = oauth2Kakao.callGetUserByAccessToken(authorization.getAccess_token());
        System.out.println(userInfoFromKakao);
        JSONParser parser = new JSONParser();
        Object obj = new Object();
        try {
            obj = parser.parse(userInfoFromKakao);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        String socialId = (String) jsonObject.get("id");
        JSONObject properties = (JSONObject) jsonObject.get("properties");
        String nickname = (String) properties.get("nickname");
        String profileImage = (String) properties.get("profile_image");

        JSONObject kakao_account = (JSONObject) jsonObject.get("kakao_account");
        String email = (String) kakao_account.get("email");

        User user = User.createUser(socialId, email, nickname, profileImage);
        return validateDuplicateUser(user, authorization.getAccess_token());
    }

    private ResponseEntity<User> validateDuplicateUser(User user, String token) {
        User findUser = userRepository.findUserBySocialId(user.getSocialId());
        User a = null;
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);
        if (findUser != a) {
            userRepository.save(findUser);
            return new ResponseEntity<User>(findUser, headers, HttpStatus.OK);
        } else {
            userRepository.save(user);
            return new ResponseEntity<User>(findUser, headers, HttpStatus.OK);
        }
    }

}
