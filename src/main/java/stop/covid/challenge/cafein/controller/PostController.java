package stop.covid.challenge.cafein.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stop.covid.challenge.cafein.dto.PostDto;
import stop.covid.challenge.cafein.service.PostService;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 등록
    @PostMapping(value = "/register")
    public ResponseEntity save(@RequestParam Long id, @RequestBody PostDto postDto) {
        return postService.save(id, postDto) ?
                new ResponseEntity(HttpStatus.OK)
                : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    // 게시글 수정
    @PatchMapping(value = "/patch/{id}")
    public ResponseEntity patchPost(@PathVariable Long id, @RequestBody PostDto postDto) {
        return postService.update(id, postDto) ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    // 게시글 삭제
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 구독한 카페의 게시물 가져오기
    @GetMapping("/subscribe")
    public ResponseEntity getSubscribePost(@RequestParam Long my_id) {
        return ResponseEntity.ok(postService.getFollowingPosts(my_id));
    }

}
