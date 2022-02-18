package stop.covid.challenge.cafein.domain.model;

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

    @OneToMany(mappedBy = "personalCafe", cascade = CascadeType.ALL)
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "personalCafe", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "personalCafe", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

//    @OneToMany(mappedBy = "follow", cascade = CascadeType.ALL)
//    private List<PersonalCafe> followers = new ArrayList<>();

//    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL)
//    private List<PersonalCafe> followings = new ArrayList<>();

    public void addUser(User user) {
        this.user = user;
        user.getPersonalCafes().add(this);
    }

}
