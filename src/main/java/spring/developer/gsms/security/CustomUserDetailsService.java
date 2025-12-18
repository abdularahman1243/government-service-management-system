package spring.developer.gsms.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.developer.gsms.entity.UserModel;
import spring.developer.gsms.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 👈 از userType یک ROLE می‌سازیم
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority("ROLE_" + user.getUserType().name());

        return new CustomUserDetails(
                user,
                List.of(authority)   // فقط یک نقش از روی userType
        );
    }
}


