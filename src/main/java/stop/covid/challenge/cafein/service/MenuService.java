package stop.covid.challenge.cafein.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import stop.covid.challenge.cafein.domain.model.HashTag;
import stop.covid.challenge.cafein.domain.model.Image;
import stop.covid.challenge.cafein.domain.model.Menu;
import stop.covid.challenge.cafein.dto.MenuDto;
import stop.covid.challenge.cafein.repository.HashTagRepository;
import stop.covid.challenge.cafein.repository.ImageRepository;
import stop.covid.challenge.cafein.repository.MenuRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final ImageRepository imageRepository;
    private final HashTagRepository hashTagRepository;

    @Transactional
    public Long save(MenuDto menuDto) {
        Menu menu = menuRepository.save(Menu.builder()
            .title(menuDto.getTitle())
            .writing(menuDto.getWriting())
            .build());

        checkImageAndHashTag(menuDto, menu);
        return menu.getId();
    }

    @Transactional
    public Boolean update(Long id, MenuDto menuDto) {
        Optional<Menu> oMenu = menuRepository.findById(id);

        if (oMenu.isEmpty())
            return false;

        Menu menu = oMenu.get();
        if (menuDto.getTitle().length() > 0) menu.setTitle(menuDto.getTitle());
        if (menuDto.getWriting().length() > 0) menu.setWriting(menu.getWriting());
        checkImageAndHashTag(menuDto, menu);

        menuRepository.save(menu);
        return true;
    }

    private void checkImageAndHashTag(MenuDto menuDto, Menu menu) {
        if (menuDto.getImages().toArray().length > 0) {
            List<Image> images = new ArrayList<>();
            for (Image image : menuDto.getImages()) {
                image.setMenu(menu);
                images.add(image);
            }
            imageRepository.saveAll(images);
        }
        if (menuDto.getHashTags().toArray().length > 0) {
            List<HashTag> hashTags = new ArrayList<>();
            for (HashTag hashTag : menuDto.getHashTags()) {
                hashTag.setMenu(menu);
                hashTags.add(hashTag);
            }
            hashTagRepository.saveAll(hashTags);
        }
    }

}
