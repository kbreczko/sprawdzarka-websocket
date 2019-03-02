package pl.wroc.uni.ii.guard.user.models.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "app_user_role")
@Data
public class UserRole {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Integer role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private UserCredentials userCredentials;
}
