package site.board.boardtraining.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import site.board.boardtraining.domain.auth.filter.JsonUsernamePasswordAuthenticationFilter;
import site.board.boardtraining.domain.auth.handler.CustomLoginFailureHandler;
import site.board.boardtraining.domain.auth.handler.CustomLoginSuccessHandler;
import site.board.boardtraining.domain.auth.handler.CustomLogoutSuccessHandler;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final UserDetailsService userDetailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationSuccessHandler customLoginSuccessHandler;
    private final AuthenticationFailureHandler customLoginFailureHandler;
    private final LogoutSuccessHandler customLogoutSuccessHandler;

    private static final List<Map<String, String>> PERMIT_ALL_PATTERNS = List.of(
            Map.of(
                    "URL", "/api/login",
                    "METHOD", POST.name()
            ),
            Map.of(
                    "URL", "/",
                    "METHOD", GET.name()
            ),
            Map.of(
                    "URL", "/api/members",
                    "METHOD", POST.name()
            )
    );

    public SecurityConfig(
            ObjectMapper objectMapper,
            UserDetailsService userDetailService,
            PasswordEncoder passwordEncoder,
            CustomLoginSuccessHandler customLoginSuccessHandler,
            CustomLoginFailureHandler customLoginFailureHandler,
            CustomLogoutSuccessHandler customLogoutSuccessHandler
    ) {
        this.objectMapper = objectMapper;
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
        this.customLoginSuccessHandler = customLoginSuccessHandler;
        this.customLoginFailureHandler = customLoginFailureHandler;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
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
                        .logoutSuccessHandler(customLogoutSuccessHandler)
                        .deleteCookies("JSESSIONID")
                );

        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                convertPermitPatternsToAntPathRequestMatchers()
                        )
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/index.html")).permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/user", GET.name())
                        )
                        .hasRole("USER")
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/admin", GET.name())
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
                        customLoginSuccessHandler,
                        customLoginFailureHandler
                );
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());

        SecurityContextRepository contextRepository = new HttpSessionSecurityContextRepository();
        jsonUsernamePasswordAuthenticationFilter.setSecurityContextRepository(contextRepository);

        return jsonUsernamePasswordAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailService);

        return new ProviderManager(provider);
    }

    private AntPathRequestMatcher[] convertPermitPatternsToAntPathRequestMatchers() {
        return PERMIT_ALL_PATTERNS.stream()
                .map(pattern -> {
                    return new AntPathRequestMatcher(
                            pattern.get("URL"),
                            pattern.get("METHOD")
                    );
                })
                .toArray(AntPathRequestMatcher[]::new);
    }
}