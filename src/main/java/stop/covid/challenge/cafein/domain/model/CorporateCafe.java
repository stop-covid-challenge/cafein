package stop.covid.challenge.cafein.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "corporate_cafe")
@Getter
@Setter
@NoArgsConstructor
public class CorporateCafe extends Cafe {
    @Id @Column(name = "corporate_cafe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "corporateCafe")
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "corporateCafe")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "corporateCafe")
    private List<Review> reviews = new ArrayList<>();

}
