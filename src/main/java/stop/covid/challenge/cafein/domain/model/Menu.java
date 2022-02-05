package stop.covid.challenge.cafein.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "menu")
@Getter
@Setter
@NoArgsConstructor
public class Menu {

    @Id @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_cafe_id")
    private PersonalCafe personalCafe;

    private String title;
    private String writing;
    private String hashtag;

    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

}
