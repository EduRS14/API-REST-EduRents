package com.ingsoft.tf.api_edurents.config;

import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByCorreo(email);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con el correo: " + email);
        }
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + user.getRol().getNombre());

        return new UserPrincipal(
                user.getId(),
                user.getCorreo(),
                user.getContrasena(),
                Collections.singleton(grantedAuthority),
                user
        );
    }

}
