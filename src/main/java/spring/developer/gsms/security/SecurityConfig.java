package spring.developer.gsms.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // ===================== PUBLIC =====================
                        .requestMatchers(
                                "/",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/uploads/**",
                                "/api/auth/**",
                                "/api/public/**",
                                "/api/instructors/**"
                        ).permitAll()

                        // ===================== DASHBOARDS =====================
                        .requestMatchers("/api/dashboard/full-admin").hasRole("FULL_ADMIN")
                        .requestMatchers("/api/dashboard/admin").hasAnyRole("ADMIN", "FULL_ADMIN")
                        .requestMatchers("/api/dashboard/teacher").hasRole("TEACHER")
                        .requestMatchers("/api/dashboard/student").hasRole("STUDENT")

                        // ===================== FULL ADMIN =====================
                        .requestMatchers("/api/admin/full/**").hasRole("FULL_ADMIN")

                        // ===================== ADMIN =====================
                        .requestMatchers("/api/admin/**").hasAnyRole("ADMIN", "FULL_ADMIN")

                        // ===================== USERS =====================
                        .requestMatchers("/api/users/**")
                        .hasAnyRole("FULL_ADMIN", "ADMIN")

                        // ===================== TEACHER =====================
                        .requestMatchers("/api/teacher/**").hasRole("TEACHER")

                        // ===================== STUDENT =====================
                        .requestMatchers("/api/student/**").hasRole("STUDENT")
                        .requestMatchers("/api/student/learn/**").authenticated()

                        .requestMatchers("/api/enrollments/**").authenticated()
                        .requestMatchers("/api/public/courses/*/lessons").authenticated()

                        // ===================== SHARED =====================
                        .requestMatchers("/api/courses/**", "/api/lessons/**")
                        .hasAnyRole("FULL_ADMIN", "ADMIN", "TEACHER", "STUDENT")

                        // ===================== DEFAULT =====================
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    //  AuthenticationManager را از Spring Boot می‌گیریم
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // PasswordEncoder برای ثبت‌نام و لاگین
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
