package stop.covid.challenge.cafein.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stop.covid.challenge.cafein.domain.model.Image;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchPostDto {
    private String writing;
    private int likeNumber;
    private List<Image> images = new ArrayList<>();
}
