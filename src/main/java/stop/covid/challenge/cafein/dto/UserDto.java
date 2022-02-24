package stop.covid.challenge.cafein.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {
    private String socialId;
    private String nickname;
    private String email;
    private String introduce;
    private String profileImage;
    private String backgroundImage;
    private String address;
}
