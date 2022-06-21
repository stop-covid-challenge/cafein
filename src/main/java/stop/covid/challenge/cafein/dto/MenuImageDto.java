package stop.covid.challenge.cafein.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuImageDto {

    private Long id;
    private String title;
    private String writing;
    private List<String> image = new ArrayList<>();

}
