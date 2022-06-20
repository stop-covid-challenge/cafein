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
import org.springframework.web.multipart.MultipartFile;
import stop.covid.challenge.cafein.domain.auth.AuthorizationKakao;
import stop.covid.challenge.cafein.domain.model.Image;
import stop.covid.challenge.cafein.domain.model.User;
import stop.covid.challenge.cafein.dto.UserDto;
import stop.covid.challenge.cafein.repository.ImageRepository;
import stop.covid.challenge.cafein.repository.UserRepository;
import stop.covid.challenge.cafein.service.auth.Oauth2Kakao;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final Oauth2Kakao oauth2Kakao;
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    public User getUserInfo(Long id) {
        User user = userRepository.findById(id).get();
        return user;
    }

    // 로그인 하기
    public ResponseEntity loginUser(UserDto userDto) {
        try {
            User user = userRepository.findUserBySocialId(userDto.getSocialId());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    // 회원가입
    @Transactional
    public User signup(UserDto userDto) {
        User user = new User();
        if (!userDto.getNickname().isEmpty()) {
            user.setNickname(userDto.getNickname());
        }
        if (!userDto.getSocialId().isEmpty()) user.setSocialId(userDto.getSocialId());
        if (!userDto.getEmail().isEmpty()) user.setEmail(userDto.getEmail());

        if (!userDto.getIntroduce().isEmpty()) user.setIntroduce(userDto.getIntroduce());
        if (!userDto.getAddress().isEmpty()) user.setAddress(userDto.getAddress());

        return userRepository.save(user);
    }

    // 프로필 변경
    @Transactional
    public User updateProfileImage(String nickname, MultipartFile multipartFile) {
        // 사용자 조회 후 기존 사진 삭제
        User user = userRepository.findUserByNickname(nickname);
        Image deleteFile = imageRepository.findByImageLink(user.getProfileImage());
        imageService.deleteFile(deleteFile);
        imageRepository.delete(deleteFile);

        // 새로운 이미지 저장 후 업데이트
        Image profile = imageService.uploadOneFile(multipartFile);
        profile.setUser(user);
        imageRepository.save(profile);
        user.setProfileImage(profile.getImageLink());
        return userRepository.save(user);
    }

    // 배경 변경
    @Transactional
    public User updateBackgroundImage(String nickname, MultipartFile multipartFile) {
        // 사용자 조회 후 기존 사진 삭제
        User user = userRepository.findUserByNickname(nickname);
        Image deleteFile = imageRepository.findByImageLink(user.getBackgroundImage());
        imageService.deleteFile(deleteFile);
        imageRepository.delete(deleteFile);

        // 새로운 이미지 저장 후 업데이트
        Image background = imageService.uploadOneFile(multipartFile);
        background.setUser(user);
        imageRepository.save(background);
        user.setBackgroundImage(background.getImageLink());
        return userRepository.save(user);
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
