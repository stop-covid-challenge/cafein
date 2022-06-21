package stop.covid.challenge.cafein.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import stop.covid.challenge.cafein.domain.model.*;
import stop.covid.challenge.cafein.dto.MenuDto;
import stop.covid.challenge.cafein.dto.MenuImageDto;
import stop.covid.challenge.cafein.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    public ResponseEntity getMenu(Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow();
        if (menu != null) {
            List<String> image = new ArrayList<>();
            List<Image> images = imageRepository.findAllByMenu(menu);
            if (images.toArray().length > 0) {
                images.forEach(element -> {
                    image.add(element.getImageLink());
                });
            }
            MenuImageDto menuImageDto = new MenuImageDto(menu.getTitle(), menu.getWriting(), image);
            return ResponseEntity.ok(menuImageDto);
        }
        return ResponseEntity.notFound().build();
    }

    public List<MenuImageDto> getAllMenu(User user) {
        List<Menu> menus = menuRepository.findAllByUser(user);
        List<MenuImageDto> menuImageDtos = new ArrayList<>();
        menus.forEach(menu -> {
            List<String> image = new ArrayList<>();
            List<Image> images = imageRepository.findAllByMenu(menu);
            if (images.toArray().length > 0) {
                images.forEach(element -> {
                    image.add(element.getImageLink());
                });
            }
            menuImageDtos.add(new MenuImageDto(menu.getTitle(), menu.getWriting(), image));
        });
        return menuImageDtos;
    }

    @Transactional
    public Menu save(String nickname, List<MultipartFile> files, MenuDto menuDto) {
        User user = userRepository.findUserByNickname(nickname);
        Menu menu = menuRepository.save(
            Menu.builder().user(user)
            .title(menuDto.getTitle())
            .writing(menuDto.getWriting())
            .build());

        if (!files.isEmpty()) {
            checkImageAndHashTag(files, menuDto, menu);
        }
        return menu;
    }

    @Transactional
    public Boolean update(Long id, List<MultipartFile> files, MenuDto menuDto) {
        Optional<Menu> oMenu = menuRepository.findById(id);

        if (oMenu.isEmpty())
            return false;

        Menu menu = oMenu.get();
        if (menuDto.getTitle().length() > 0) menu.setTitle(menuDto.getTitle());
        if (menuDto.getWriting().length() > 0) menu.setWriting(menu.getWriting());
        Menu savedMenu = menuRepository.save(menu);
        if (!files.isEmpty()) {
            checkImageAndHashTag(files, menuDto, savedMenu);
        }
        return true;
    }

    @Transactional
    public void delete(Long id) {
        Menu menu = menuRepository.findById(id).get();
        List<Image> images = menu.getImages();
        for (Image image : images) {
            imageService.deleteFile(image);
        }
        menuRepository.delete(menu);
    }

    private void checkImageAndHashTag(List<MultipartFile> files, MenuDto menuDto, Menu menu) {
        if (files.toArray().length > 0) {
            List<Image> images = imageService.uploadFile(files);
            for (Image image : images) {
                image.setMenu(menu);
            }
            List<Image> imageList = imageRepository.saveAll(images);
            menu.addMenuImage(imageList);
            menuRepository.save(menu);
        }
//        if (menuDto.getHashTags().toArray().length > 0) {
//            List<HashTag> hashTags = new ArrayList<>();
//            for (HashTag hashTag : menuDto.getHashTags()) {
//                hashTag.setMenu(menu);
//                hashTags.add(hashTag);
//            }
//            hashTagRepository.saveAll(hashTags);
//        }
    }

}
