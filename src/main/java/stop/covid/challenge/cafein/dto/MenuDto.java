package stop.covid.challenge.cafein.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import stop.covid.challenge.cafein.domain.model.HashTag;
import stop.covid.challenge.cafein.domain.model.Image;
import stop.covid.challenge.cafein.domain.model.Menu;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MenuDto {

    private String title;
    private String writing;
    private List<HashTag> hashTags = new ArrayList<>();
    private List<Image> images = new ArrayList<>();

}
