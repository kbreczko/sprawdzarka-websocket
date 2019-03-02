package pl.wroc.uni.ii.guard.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wroc.uni.ii.guard.common.enums.Role;
import pl.wroc.uni.ii.guard.user.repositories.UserCredentialsRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class UserCredentialsService {
    private final UserCredentialsRepository userCredentialsRepository;

    @Autowired
    public UserCredentialsService(UserCredentialsRepository userCredentialsRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
    }

    @Transactional(readOnly = true)
    public Optional<UserDetails> getUserDetailsOptional(String email) {
        return userCredentialsRepository.findByEmail(email)
            .map(userCredentials -> {
                String roleName = Role.fromId(userCredentials.getUserRole().getRole()).getName();
                Collection<? extends GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(roleName));
                return new User(userCredentials.getEmail(), "", authorities);
            });
    }
}
