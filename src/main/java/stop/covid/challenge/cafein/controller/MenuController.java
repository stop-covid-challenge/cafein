package stop.covid.challenge.cafein.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stop.covid.challenge.cafein.domain.model.Menu;
import stop.covid.challenge.cafein.domain.model.User;
import stop.covid.challenge.cafein.dto.MenuDto;
import stop.covid.challenge.cafein.dto.MenuImageDto;
import stop.covid.challenge.cafein.repository.UserRepository;
import stop.covid.challenge.cafein.service.MenuService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final UserRepository userRepository;
    private final MenuService menuService;

    @ApiOperation(value = "메뉴 전체 조회", notes = "메뉴 전체 조회")
    @GetMapping(value = "/get")
    public ResponseEntity<List<MenuImageDto>> getAllMenu(@RequestParam String nickname) {
        User user = userRepository.findUserByNickname(nickname);
        List<MenuImageDto> menus = menuService.getAllMenu(user);
//        Map<String, Object> result = new HashMap<>();
//        result.put("data", menus);
        return ResponseEntity.ok(menus);
    }

    @ApiOperation(value = "메뉴 하나 조회", notes = "메뉴 하나 조회")
    @GetMapping(value = "/get/{id}")
    public ResponseEntity getOneMenu(@PathVariable("id") Long id) {
        return menuService.getMenu(id);
    }

    @ApiOperation(value = "메뉴 등록", notes = "메뉴 등록")
    @PostMapping(value = "/post", consumes = { "multipart/form-data" })
    public ResponseEntity<Menu> postMenu(
        @RequestPart("images") List<MultipartFile> images,
        @RequestParam("title") String title,
        @RequestParam("writing") String writing,
        @RequestParam("nickname") String nickname
    ) {
        MenuDto menuDto = new MenuDto(title, writing);
        Menu menu = menuService.save(nickname, images, menuDto);
        return new ResponseEntity<Menu>(menu, HttpStatus.OK) ;
    }

    @ApiOperation(value = "메뉴 수정", notes = "메뉴 수정")
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

    @ApiOperation(value = "메뉴 삭제", notes = "메뉴 삭제")
    @DeleteMapping(value = "/delete/{menu-id}")
    public ResponseEntity<?> deleteMenu(@PathVariable(name = "menu-id") Long menu_id) {
        menuService.delete(menu_id);
        return ResponseEntity.noContent().build();
    }


}
