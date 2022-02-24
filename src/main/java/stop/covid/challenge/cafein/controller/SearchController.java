package stop.covid.challenge.cafein.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stop.covid.challenge.cafein.service.SearchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/get")
    public ResponseEntity getSearchResult(@RequestParam(name = "searchKeyword") String searchKeyword) {
        return searchService.getSearchResult(searchKeyword);
    }

}
