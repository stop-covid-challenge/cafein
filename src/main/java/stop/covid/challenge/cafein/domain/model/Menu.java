package stop.covid.challenge.cafein.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import stop.covid.challenge.cafein.dto.MenuImageDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "menu")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends BaseTimeEntity {

    @Id @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String writing;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Image> images = new ArrayList<>();

    public void addMenuImage(List<Image> imageList) { this.images = imageList; }

}
