package stop.covid.challenge.cafein.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stop.covid.challenge.cafein.domain.model.*;
import stop.covid.challenge.cafein.dto.PostDto;
import stop.covid.challenge.cafein.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FollowingRepository followingRepository;
    private final ImageRepository imageRepository;
    private final HashTagRepository hashTagRepository;
    private final CommentRepository commentRepository;

    // 포스트 등록
    @Transactional
    public Boolean save(Long id, PostDto postDto) {
        // 먼저 id로 계정 조회 후 가져옴
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();
        Post post = postRepository.save(Post.builder().writing(postDto.getWriting()).likeNumber(postDto.getLikeNumber()).user(user).build());
        checkImageAndHashTagAndComment(postDto, post);
        return true;
    }

    // 포스트 수정
    @Transactional
    public Boolean update(Long id, PostDto postDto) {
        Optional<Post> oPost = postRepository.findById(id);

        if (oPost.isEmpty())
            return false;

        Post post = oPost.get();
        if (postDto.getLikeNumber() >= 0) post.setLikeNumber(postDto.getLikeNumber());
        if (postDto.getWriting().length() > 0) post.setWriting(postDto.getWriting());
        checkImageAndHashTagAndComment(postDto, post);

        postRepository.save(post);
        return true;
    }

    // 포스트 삭제
    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    // 내가 팔로잉하고 있는 카페의 최신게시글 표시
    public List<Post> getFollowingPosts(Long my_id) {
        List<Long> followingIds = new ArrayList<>();
        User user = userRepository.findById(my_id).get();
        List<Following> followings = followingRepository.findAllByUser(user);
        followings.forEach(following -> {
            followingIds.add(following.getFollower_id());
        });

        return postRepository.findAllById(followingIds);
    }

    private void checkImageAndHashTagAndComment(PostDto postDto, Post post) {
        if (postDto.getImages().toArray().length > 0) {
            List<Image> images = new ArrayList<>();
            for (Image image : postDto.getImages()) {
                image.setPost(post);
                images.add(image);
            }
            imageRepository.saveAll(images);
        }
        if (postDto.getHashTags().toArray().length > 0) {
            List<HashTag> hashTags = new ArrayList<>();
            for (HashTag hashTag : postDto.getHashTags()) {
                hashTag.setPost(post);
                hashTags.add(hashTag);
            }
            hashTagRepository.saveAll(hashTags);
        }
        if (postDto.getComments().toArray().length > 0) {
            List<Comment> comments = new ArrayList<>();
            for (Comment comment : postDto.getComments()) {
                comment.setPost(post);
                comments.add(comment);
            }
            commentRepository.saveAll(comments);
        }
    }

}
