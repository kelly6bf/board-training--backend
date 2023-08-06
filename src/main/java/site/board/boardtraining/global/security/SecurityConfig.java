package site.board.boardtraining.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import site.board.boardtraining.auth.filter.JsonIdPwAuthenticationFilter;

import java.util.stream.Stream;

import static org.springframework.http.HttpMethod.POST;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    private static final RequestMatcher LOGIN_REQUEST_MATCHER = new AntPathRequestMatcher("/api/login", POST.name());
    private static final String[] PERMIT_ALL_PATTERNS = new String[]{
            "/api/login"
    };

    public SecurityConfig(
            UserDetailsService userDetailsService,
            AuthenticationConfiguration authenticationConfiguration
    ) {
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable);

        http.addFilterAt(
                new JsonIdPwAuthenticationFilter(
                        authenticationConfiguration.getAuthenticationManager(),
                        LOGIN_REQUEST_MATCHER
                ),
                UsernamePasswordAuthenticationFilter.class
        );

        http.userDetailsService(userDetailsService);

        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                Stream
                                        .of(PERMIT_ALL_PATTERNS)
                                        .map(AntPathRequestMatcher::antMatcher)
                                        .toArray(AntPathRequestMatcher[]::new)
                        )
                        .permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}