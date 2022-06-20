package stop.covid.challenge.cafein.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stop.covid.challenge.cafein.domain.model.Comment;
import stop.covid.challenge.cafein.domain.model.Post;
import stop.covid.challenge.cafein.domain.model.User;
import stop.covid.challenge.cafein.dto.CommentDto;
import stop.covid.challenge.cafein.repository.CommentRepository;
import stop.covid.challenge.cafein.repository.PostRepository;
import stop.covid.challenge.cafein.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 내가 쓴 댓글 가져오기
    public List<Comment> getMyComments(Long my_id) {
        User user = userRepository.findById(my_id).get();
        List<Comment> comments = commentRepository.findAllByUser(user);
        return comments;
    }

    // 내가 특정 게시물에 썼던 댓글 가져오기
    public Comment getMyCommentInPost(Long my_id, Long post_id) {
        User user = userRepository.findById(my_id).get();
        Post post = postRepository.findById(post_id).get();
        return commentRepository.findCommentByUserAndPost(user, post);
    }

    // 댓글 작성하기
    @Transactional
    public Comment writeComment(CommentDto commentDto) {
        User user = userRepository.findById(commentDto.getMy_id()).orElseThrow();
        Post post = postRepository.findById(commentDto.getPost_id()).orElseThrow();
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setWriting(comment.getWriting());
        return comment;
    }

    // 댓글 삭제하기
    @Transactional
    public void deleteComment(Long my_id, Long post_id) {
        User user = userRepository.findById(my_id).orElseThrow();
        Post post = postRepository.findById(post_id).orElseThrow();
        Comment comment = commentRepository.findCommentByUserAndPost(user, post);
        commentRepository.delete(comment);
    }


}
