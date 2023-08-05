package site.board.boardtraining.global.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {

        // TODO: 인증 기능 도입 후 사용자 personalId로 자동 주입 되도록 리팩토링
        return () -> Optional.of("tester");
    }
}