package homework8Gradle.homework8Gradle.security;

import homework8Gradle.homework8Gradle.model.dao.User;
import homework8Gradle.homework8Gradle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("user with username %s not exists", username)));;
        return new UserPrincipal(user);
    }
}
