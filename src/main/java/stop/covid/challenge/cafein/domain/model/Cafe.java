package stop.covid.challenge.cafein.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Cafe {
    private String nickname;
    private String introduce;
    private String profileImage;
    private String backgroundImage;
    private String address;
}
