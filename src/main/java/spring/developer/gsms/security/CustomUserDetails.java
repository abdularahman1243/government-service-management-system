package spring.developer.gsms.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import spring.developer.gsms.entity.UserModel;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {

    private final UserModel user;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UserModel user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public Long getId() {
        return user.getId();
    }

    public String getRole() {
        return "ROLE_" + user.getUserType().name();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return user.getEnabled(); }
}

