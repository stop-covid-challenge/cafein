package stop.covid.challenge.cafein.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import stop.covid.challenge.cafein.domain.model.HashTag;
import stop.covid.challenge.cafein.domain.model.Image;
import stop.covid.challenge.cafein.domain.model.Menu;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {

    private String title;
    private String writing;

}
