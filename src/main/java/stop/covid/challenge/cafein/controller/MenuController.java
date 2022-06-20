package stop.covid.challenge.cafein.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stop.covid.challenge.cafein.domain.model.Menu;
import stop.covid.challenge.cafein.domain.model.PersonalCafe;
import stop.covid.challenge.cafein.dto.MenuDto;
import stop.covid.challenge.cafein.repository.MenuRepository;
import stop.covid.challenge.cafein.repository.PersonalCafeRepository;
import stop.covid.challenge.cafein.service.MenuService;

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
    private final MenuService menuService;

    // 메뉴 조회
    @GetMapping(value = "/get")
    public ResponseEntity<Map<String, Object>> getMenu(@RequestParam String nickname) {
        PersonalCafe personalCafe = personalCafeRepository.findPersonalCafeByNickname(nickname);
        List<Menu> menus = menuRepository.findAllByPersonalCafe(personalCafe);
        Map<String, Object> result = new HashMap<>();
        result.put("data", menus);
        return ResponseEntity.ok(result);
    }

    // 메뉴 등록
    @PostMapping(value = "/post")
    public ResponseEntity<Long> postMenu(@RequestBody MenuDto menuDto) {
        return new ResponseEntity<Long>(menuService.save(menuDto), HttpStatus.OK) ;
    }

    // 메뉴 수정
    @PatchMapping(value = "/patch/{id}")
    public ResponseEntity patchMenu(@PathVariable Long id, @RequestBody MenuDto menuDto) {
        return menuService.update(id, menuDto) ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    // 메뉴 삭제
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable Long id) {
        menuRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
