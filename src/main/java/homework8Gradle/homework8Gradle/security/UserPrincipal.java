package homework8Gradle.homework8Gradle.security;

import homework8Gradle.homework8Gradle.model.dao.Role;
import homework8Gradle.homework8Gradle.model.dao.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private final User user;
    @Override
            public Collection<SimpleGrantedAuthority> getAuthorities() {
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
                return authorities;
            }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
