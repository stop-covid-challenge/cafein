package stop.covid.challenge.cafein.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stop.covid.challenge.cafein.domain.type.CafeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "personal_cafe")
@Getter
@Setter
@NoArgsConstructor
public class PersonalCafe extends Cafe {
    @Id @Column(name = "personal_cafe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private CafeType cafeType;

    public void addUser(User user) {
        this.user = user;
    }

}
