package stop.covid.challenge.cafein.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "menu")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_cafe_id")
    private PersonalCafe personalCafe;

    private String title;
    private String writing;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<HashTag> hashTags = new ArrayList<>();

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    public void addPersonalCafe(PersonalCafe personalCafe) {
        this.personalCafe = personalCafe;
        personalCafe.getMenus().add(this);
    }

}
