package stop.covid.challenge.cafein.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import stop.covid.challenge.cafein.dto.FollowBasicDto;
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

    // 팔로우 신청 or 팔로우 취소
    @PostMapping("/post")
    public ResponseEntity basicFollow(@RequestBody FollowBasicDto followBasicDto) {
        return followService.setFollow(followBasicDto.getMy_id(), followBasicDto.getOther_id())
            ? ResponseEntity.status(HttpStatus.ACCEPTED).body("created")
            : ResponseEntity.status(HttpStatus.ACCEPTED).body("deleted");
    }

}
