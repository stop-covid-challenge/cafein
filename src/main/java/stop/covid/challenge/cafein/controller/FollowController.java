package stop.covid.challenge.cafein.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import stop.covid.challenge.cafein.service.FollowService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    // 팔로워 목록 조회
    @GetMapping("/followers")
    public ResponseEntity getFollowers(@RequestParam Long id) {
        return ResponseEntity.status(200).body(followService.getFollower(id));
    }

    // 팔로잉 목록 조회
    @GetMapping("/following")
    public ResponseEntity getFollowings(@RequestParam Long id) {
        return ResponseEntity.status(200).body(followService.getFollowing(id));
    }

}
