package stop.covid.challenge.cafein.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import stop.covid.challenge.cafein.domain.model.*;
import stop.covid.challenge.cafein.dto.PostDto;
import stop.covid.challenge.cafein.dto.PostImageDto;
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
    private final ImageService imageService;

    public List<PostImageDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostImageDto> postImageDtos = new ArrayList<>();
        posts.forEach(post -> {
            List<String> image = new ArrayList<>();
            List<Image> images = imageRepository.findAllByPost(post);
            if (images.toArray().length > 0){
                images.forEach(image1 -> {
                    image.add(image1.getImageLink());
                });
            }
            postImageDtos.add(new PostImageDto(post.getId(), post.getWriting(), post.getLikeNumber(), image));
        });
        return postImageDtos;
    }

    // 내가 팔로잉하고 있는 카페의 최신게시글 표시
    public List<PostImageDto> getFollowingPosts(Long my_id) {
        List<User> followingIds = new ArrayList<>();
        User user = userRepository.findById(my_id).get();
        List<Following> followings = followingRepository.findAllByUser(user);
        followings.forEach(following -> {
            followingIds.add(userRepository.findById(following.getFollower_id()).orElseThrow());
        });

        List<Post> posts = new ArrayList<>();
        followingIds.forEach(id -> {
            posts.addAll(postRepository.findAllByUser(id));
        });
        List<PostImageDto> postImageDtos = new ArrayList<>();
        posts.forEach(post -> {
            List<String> image = new ArrayList<>();
            List<Image> images = imageRepository.findAllByPost(post);
            if (images.toArray().length > 0){
                images.forEach(image1 -> {
                    image.add(image1.getImageLink());
                });
            }
            postImageDtos.add(new PostImageDto(post.getId(), post.getWriting(), post.getLikeNumber(), image));
        });
        return postImageDtos;
    }

    // 포스트 등록
    @Transactional
    public Boolean save(Long id, String writing, List<MultipartFile> files) {
        // 먼저 id로 계정 조회 후 가져옴
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();
        Post post = postRepository.save(Post.builder().writing(writing).likeNumber(0).user(user).build());
        checkImageAndHashTagAndComment(files, new PostDto(writing, 0), post);
        return true;
    }

    // 포스트 수정
    @Transactional
    public Boolean update(Long id, PostDto postDto, List<MultipartFile> files) {
        Optional<Post> oPost = postRepository.findById(id);

        if (oPost.isEmpty())
            return false;

        Post post = oPost.get();
        if (postDto.getLikeNumber() >= 0) post.setLikeNumber(postDto.getLikeNumber());
        if (postDto.getWriting().length() > 0) post.setWriting(postDto.getWriting());
        if (!files.isEmpty()) {
            checkImageAndHashTagAndComment(files, postDto, post);
        }
        postRepository.save(post);
        return true;
    }

    // 포스트 삭제
    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id).get();
        List<Image> images = post.getImages();
        for (Image image : images) {
            imageService.deleteFile(image);
        }
        postRepository.delete(post);
    }

    private void checkImageAndHashTagAndComment(List<MultipartFile> files, PostDto postDto, Post post) {
        if (files.toArray().length > 0) {
            List<Image> images = imageService.uploadFile(files);
            for (Image image : images) {
                image.setPost(post);
            }
            List<Image> imageList = imageRepository.saveAll(images);
            post.addPostImage(imageList);
            postRepository.save(post);
        }
    }

}
