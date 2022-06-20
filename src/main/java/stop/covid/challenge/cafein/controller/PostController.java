package stop.covid.challenge.cafein.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stop.covid.challenge.cafein.dto.PostDto;
import stop.covid.challenge.cafein.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ApiOperation(value = "모든 게시물 가져오기", notes = "모든 게시물 가져오기")
    @GetMapping("/all")
    public ResponseEntity getAllPost() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @ApiOperation(value = "구독한 카페의 게시물 가져오기", notes = "구독한 카페의 게시물 가져오기")
    @GetMapping("/subscribe")
    public ResponseEntity getSubscribePost(@RequestParam Long my_id) {
        return ResponseEntity.ok(postService.getFollowingPosts(my_id));
    }

    @ApiOperation(value = "게시글 등록", notes = "게시글 등록")
    @PostMapping(value = "/register", consumes = { "multipart/form-data" })
    public ResponseEntity save(@RequestParam(name = "user-id") Long user_id,
                               @RequestParam(name = "writing") String writing,
                               @RequestPart(name = "images") List<MultipartFile> files) {
        return postService.save(user_id, writing, files) ?
                new ResponseEntity(HttpStatus.OK)
                : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글 수정")
    @PatchMapping(value = "/patch/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity patchPost(
        @RequestParam(name = "post-id") Long id,
        @RequestParam(name = "writing") String writing,
        @RequestParam(name = "likeNumber") int likeNumber,
        @RequestPart(name = "images") List<MultipartFile> files
    ) {
        return postService.update(id, new PostDto(writing, likeNumber), files) ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글 삭제")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
