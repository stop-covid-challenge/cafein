package stop.covid.challenge.cafein.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    private final PersonalCafeRepository personalCafeRepository;
    private final MenuService menuService;

    // 메뉴 전체 조회
    @GetMapping(value = "/get")
    public ResponseEntity<Map<String, Object>> getAllMenu(@RequestParam String nickname) {
        PersonalCafe personalCafe = personalCafeRepository.findPersonalCafeByNickname(nickname);
        List<Menu> menus = menuService.getAllMenu(personalCafe);
        Map<String, Object> result = new HashMap<>();
        result.put("data", menus);
        return ResponseEntity.ok(result);
    }

    // 메뉴 하나 조회
    @GetMapping(value = "/get/{menu-id}")
    public ResponseEntity<Map<String, Object>> getOneMenu(@PathVariable Long id) {
        Menu menu = menuService.getMenu(id);
        Map<String, Object> result = new HashMap<>();
        result.put("menu", menu);
        return ResponseEntity.ok(result);
    }

    // 메뉴 등록
    @PostMapping(value = "/post", consumes = { "multipart/form-data" })
    public ResponseEntity<Long> postMenu(
        @RequestPart("images") List<MultipartFile> images,
        @RequestParam("title") String title,
        @RequestParam("writing") String writing,
        @RequestParam("nickname") String nickname
    ) {
        MenuDto menuDto = new MenuDto(title, writing);
        return new ResponseEntity<Long>(menuService.save(nickname, images, menuDto), HttpStatus.OK) ;
    }

    // 메뉴 수정
    @PatchMapping(value = "/patch/{menu-id}", consumes = { "multipart/form-data" })
    public ResponseEntity patchMenu(
        @PathVariable(name = "menu-id") Long id,
        @RequestPart("images") List<MultipartFile> images,
        @RequestParam("title") String title,
        @RequestParam("writing") String writing
    ) {
        MenuDto menuDto = new MenuDto(title, writing);
        return menuService.update(id, images, menuDto) ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    // 메뉴 삭제
    @DeleteMapping(value = "/delete/{menu-id}")
    public ResponseEntity<?> deleteMenu(@PathVariable(name = "menu-id") Long menu_id) {
        menuService.delete(menu_id);
        return ResponseEntity.noContent().build();
    }


}
