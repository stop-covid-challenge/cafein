package stop.covid.challenge.cafein.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import stop.covid.challenge.cafein.domain.model.Menu;
import stop.covid.challenge.cafein.dto.MenuDto;
import stop.covid.challenge.cafein.repository.MenuRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public Long save(MenuDto menuDto) {
        return menuRepository.save(menuDto.toEntity()).getId();
    }

    @Transactional
    public Boolean update(Long id, MenuDto menuDto) {
        Optional<Menu> oMenu = menuRepository.findById(id);

        if (oMenu.isEmpty())
            return false;

        Menu menu = oMenu.get();
        if (menuDto.getTitle().length() > 0) menu.setTitle(menuDto.getTitle());
        if (menuDto.getWriting().length() > 0) menu.setWriting(menu.getWriting());
        if (menuDto.getImages().toArray().length > 0) menu.setImages(menuDto.getImages());
        if (menuDto.getHashTags().toArray().length > 0) menu.setHashTags(menuDto.getHashTags());

        menuRepository.save(menu);
        return true;
    }

}
