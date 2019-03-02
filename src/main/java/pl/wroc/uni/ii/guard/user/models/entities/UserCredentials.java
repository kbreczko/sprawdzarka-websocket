package pl.wroc.uni.ii.guard.user.models.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "app_user_credentials")
@Data
public class UserCredentials {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String firstName;

    private String lastName;

    private String photo;

    @Column(nullable = false)
    private Boolean activated = false;

    @OneToOne(mappedBy = "userCredentials", cascade = {CascadeType.ALL})
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UserRole userRole;
}
