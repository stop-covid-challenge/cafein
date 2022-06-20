package stop.covid.challenge.cafein.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import stop.covid.challenge.cafein.dto.CommentDto;
import stop.covid.challenge.cafein.service.CommentService;

@Repository
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/total")
    public ResponseEntity getMyTotalComment(@RequestParam Long my_id) {
        return ResponseEntity.ok(commentService.getMyComments(my_id));
    }

    @GetMapping("/{post-id}/get")
    public ResponseEntity getPostComment(@PathVariable("post-id") Long post_id, @RequestParam Long my_id) {
        return ResponseEntity.ok(commentService.getMyCommentInPost(my_id, post_id));
    }

    @PostMapping("/write")
    public ResponseEntity writeComment(@RequestBody CommentDto commentDto) {
        commentService.writeComment(commentDto);
        return ResponseEntity.ok("completed");
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteComment(@RequestParam Long my_id, @RequestParam Long post_id) {
        commentService.deleteComment(my_id, post_id);
        return ResponseEntity.ok("completed");
    }

}
