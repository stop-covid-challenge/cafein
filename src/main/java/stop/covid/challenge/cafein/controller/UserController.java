package stop.covid.challenge.cafein.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stop.covid.challenge.cafein.domain.model.User;
import stop.covid.challenge.cafein.dto.UserDto;
import stop.covid.challenge.cafein.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "특정 사용자 정보 가져오기", notes = "특정 사용자 정보 가져오기")
    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserInfo(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserInfo(id));
    }

    @ApiOperation(value = "로그인", notes = "로그인")
    @GetMapping("/login")
    public ResponseEntity login(
        @RequestParam String socialId
    ) {
        return userService.loginUser(socialId);
    }

    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserDto userDto) {
        User user = userService.signup(userDto);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "프로필 이미지 변경하기", notes = "프로필 이미지 변경하기")
    @PutMapping(value = "/profileImage", consumes = { "multipart/form-data" })
    public ResponseEntity updateProfileImage(
        @RequestParam(name = "nickname") String nickname,
        @RequestPart(name = "profileImage") MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(userService.updateProfileImage(nickname, multipartFile));
    }

    @ApiOperation(value = "배경 이미지 변경하기", notes = "배경 이미지 변경하기")
    @PutMapping(value = "/backgroundImage", consumes = { "multipart/form-data" })
    public ResponseEntity updateBackgroundImage(
        @RequestParam(name = "nickname") String nickname,
        @RequestPart(name = "backgroundImage") MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(userService.updateBackgroundImage(nickname, multipartFile));
    }

//    @GetMapping(value = "/kakao")
//    public ResponseEntity kakaoOauthRedirect(@RequestParam String code) {
//        ResponseEntity responseEntity = userService.oauth2AuthorizationKakao(code);
//        HttpHeaders headers = responseEntity.getHeaders();
//
////        "redirect:webauthcallback://success?customToken="+headers.get("token").toString()
//        return responseEntity;
//    }

}
