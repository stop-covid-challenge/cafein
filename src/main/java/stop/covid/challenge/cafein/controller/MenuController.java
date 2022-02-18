package stop.covid.challenge.cafein.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stop.covid.challenge.cafein.domain.model.Menu;
import stop.covid.challenge.cafein.domain.model.PersonalCafe;
import stop.covid.challenge.cafein.dto.MenuDto;
import stop.covid.challenge.cafein.repository.MenuRepository;
import stop.covid.challenge.cafein.repository.PersonalCafeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuRepository menuRepository;
    private final PersonalCafeRepository personalCafeRepository;

    // 메뉴 조회
    @GetMapping(value = "/get")
    public ResponseEntity<Map<String, Object>> getMenu(@RequestParam String nickname) {
        PersonalCafe personalCafe = personalCafeRepository.findPersonalCafeByNickname(nickname);
        List<Menu> menus = personalCafe.getMenus();
        Map<String, Object> result = new HashMap<>();
        result.put("data", menus);
        return ResponseEntity.ok(result);
    }

    // 메뉴 등록
    @PostMapping(value = "/post")
    public ResponseEntity<?> postMenu(@RequestBody MenuDto menuDto) {



        return null;
    }

    // 메뉴 삭제
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable Long id) {
        menuRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
