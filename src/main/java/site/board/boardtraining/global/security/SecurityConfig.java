package site.board.boardtraining.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import site.board.boardtraining.auth.filter.JsonUsernamePasswordAuthenticationFilter;
import site.board.boardtraining.auth.handler.LoginFailureHandler;
import site.board.boardtraining.auth.handler.LoginSuccessHandler;

import java.util.stream.Stream;

@Configuration
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final UserDetailsService loginService;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;

    private static final String[] PERMIT_ALL_PATTERNS = new String[]{
            "/api/login"
    };

    public SecurityConfig(
            ObjectMapper objectMapper,
            UserDetailsService loginService,
            LoginSuccessHandler loginSuccessHandler,
            LoginFailureHandler loginFailureHandler
    ) {
        this.objectMapper = objectMapper;
        this.loginService = loginService;
        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailureHandler = loginFailureHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        http
                .logout(logout -> logout
                    .logoutUrl("/api/logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                );

        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                Stream
                                        .of(PERMIT_ALL_PATTERNS)
                                        .map(AntPathRequestMatcher::antMatcher)
                                        .toArray(AntPathRequestMatcher[]::new)
                        )
                        .permitAll()
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/user")
                        )
                        .hasRole("USER")
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/admin")
                        )
                        .hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        http
                .addFilterBefore(
                    jsonUsernamePasswordAuthenticationFilter(),
                    UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter() {
        JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter =
                new JsonUsernamePasswordAuthenticationFilter(
                        objectMapper,
                        loginSuccessHandler,
                        loginFailureHandler
                );
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());

        SecurityContextRepository contextRepository = new HttpSessionSecurityContextRepository();
        jsonUsernamePasswordAuthenticationFilter.setSecurityContextRepository(contextRepository);

        return jsonUsernamePasswordAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(loginService);

        return new ProviderManager(provider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}