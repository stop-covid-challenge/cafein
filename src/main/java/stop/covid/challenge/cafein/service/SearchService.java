package stop.covid.challenge.cafein.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stop.covid.challenge.cafein.domain.model.*;
import stop.covid.challenge.cafein.dto.*;
import stop.covid.challenge.cafein.repository.MenuRepository;
import stop.covid.challenge.cafein.repository.PostRepository;
import stop.covid.challenge.cafein.repository.ProductRepository;
import stop.covid.challenge.cafein.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final HashTagService hashTagService;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final PostRepository postRepository;
    private final ProductRepository productRepository;

    public ResponseEntity getSearchResult(String searchKeyword) {
        if (searchKeyword.contains("#")) {
            try {
                List<HashTag> hashTags = hashTagService.getHashTag(searchKeyword);
                ArrayList<SearchMenuDto> searchMenuDtos = new ArrayList<>();
                ArrayList<SearchPostDto> searchPostDtos = new ArrayList<>();
                ArrayList<SearchProductDto> searchProductDtos = new ArrayList<>();

                hashTags.forEach(hashTag -> {
                    if (hashTag.getMenu() != null) searchMenuDtos.add(changeHashTagIntoMenu(hashTag.getMenu().getId()));
                    if (hashTag.getPost() != null) searchPostDtos.add(changeHashTagIntoPost(hashTag.getPost().getId()));
                    if (hashTag.getProduct() != null) searchProductDtos.add(changeHashTagIntoProduct(hashTag.getProduct().getId()));
                });

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("menu", searchMenuDtos);
                map.put("post", searchPostDtos);
                map.put("product", searchProductDtos);
                return ResponseEntity.ok(map);
            } catch (Exception e) {
                e.printStackTrace();
                return (ResponseEntity) ResponseEntity.notFound();
            }
        } else {
            try {
                List<User> users = userRepository.findAllByNickname(searchKeyword);
                ArrayList<SearchUserDto> searchUserDtos = new ArrayList<>();

                users.forEach(user -> {
                    searchUserDtos.add(changeUser(user));
                });

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("user", searchUserDtos);
                return ResponseEntity.ok(map);

            } catch (Exception e) {
                e.printStackTrace();
                return (ResponseEntity) ResponseEntity.notFound();
            }
        }
    }

    private SearchMenuDto changeHashTagIntoMenu(Long id) {
        Menu menu = menuRepository.findById(id).get();
        SearchMenuDto searchMenuDto = new SearchMenuDto();
        searchMenuDto.setImages(menu.getImages());
        searchMenuDto.setWriting(menu.getWriting());
        searchMenuDto.setTitle(menu.getTitle());
        return searchMenuDto;
    }

    private SearchPostDto changeHashTagIntoPost(Long id) {
        Post post = postRepository.findById(id).get();
        SearchPostDto searchPostDto = new SearchPostDto();
        searchPostDto.setImages(post.getImages());
        searchPostDto.setWriting(post.getWriting());
        searchPostDto.setLikeNumber(post.getLikeNumber());
        return searchPostDto;
    }

    private SearchProductDto changeHashTagIntoProduct(Long id) {
        Product product = productRepository.findById(id).get();
        SearchProductDto searchProductDto = new SearchProductDto();
        searchProductDto.setImages(product.getImages());
        searchProductDto.setTitle(product.getTitle());
        searchProductDto.setPrice(product.getPrice());
        return searchProductDto;
    }

    private SearchUserDto changeUser(User user) {
        SearchUserDto searchUserDto = new SearchUserDto();
        searchUserDto.setEmail(user.getEmail());
        searchUserDto.setNickname(user.getNickname());
        searchUserDto.setProfileImage(user.getProfileImage());
        return searchUserDto;
    }

}
