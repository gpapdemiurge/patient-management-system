package com.gpapdemiurge.backend.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gpapdemiurge.backend.entity.Role;
import com.gpapdemiurge.backend.entity.User;
import com.gpapdemiurge.backend.repository.UserRepository;

/**
 * Loads a {@link User} from the database by username and adapts it into a
 * Spring Security {@link UserDetails} principal.
 *
 * <p>The user's {@link Role} (ADMIN or DOCTOR) is converted into a single
 * {@link GrantedAuthority} so that the JWT can carry the role and the
 * frontend can redirect the user to the appropriate panel.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username: " + username));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(List.of(authority))
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
