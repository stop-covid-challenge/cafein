package stop.covid.challenge.cafein.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stop.covid.challenge.cafein.domain.model.Menu;
import stop.covid.challenge.cafein.domain.model.PersonalCafe;
import stop.covid.challenge.cafein.domain.model.Post;
import stop.covid.challenge.cafein.dto.MenuDto;
import stop.covid.challenge.cafein.dto.PostDto;
import stop.covid.challenge.cafein.repository.PersonalCafeRepository;
import stop.covid.challenge.cafein.repository.PostRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PersonalCafeRepository personalCafeRepository;
    private final PostRepository postRepository;

    // 포스트 등록
    @Transactional
    public Boolean save(Long id, PostDto postDto) {
        // 먼저 id로 계정 조회 후 가져옴
        Optional<PersonalCafe> optionalPersonalCafe = personalCafeRepository.findById(id);

        if (optionalPersonalCafe.isEmpty()) {
            return false;
        }

        PersonalCafe personalCafe = optionalPersonalCafe.get();
        Post post = postDto.toEntity();
        post.setPersonalCafe(personalCafe);
        postRepository.save(post);
        return true;
    }

    @Transactional
    public Boolean update(Long id, PostDto postDto) {
        Optional<Post> oPost = postRepository.findById(id);

        if (oPost.isEmpty())
            return false;

        Post post = oPost.get();
        if (postDto.getLike() >= 0) post.setLike(postDto.getLike());
        if (postDto.getWriting().length() > 0) post.setWriting(postDto.getWriting());
        if (postDto.getImages().toArray().length > 0) post.setImages(postDto.getImages());
        if (postDto.getHashTags().toArray().length > 0) post.setHashTags(postDto.getHashTags());
        if (postDto.getComments().toArray().length > 0) post.setComments(postDto.getComments());

        postRepository.save(post);
        return true;
    }

    // 포스트 삭제
    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

}
