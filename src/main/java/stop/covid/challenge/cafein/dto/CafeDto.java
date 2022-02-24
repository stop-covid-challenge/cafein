package stop.covid.challenge.cafein.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CafeDto {
    private Long user_id;
    private String nickname;
    private String introduce;
    private String profileImage;
    private String backgroundImage;
    private String address;
}
