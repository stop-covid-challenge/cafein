package stop.covid.challenge.cafein.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stop.covid.challenge.cafein.domain.model.User;
import stop.covid.challenge.cafein.dto.UserDto;
import stop.covid.challenge.cafein.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserInfo(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserInfo(id));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDto userDto) {
        User user = userService.loginRegister(userDto);
        return ResponseEntity.ok(user);
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
