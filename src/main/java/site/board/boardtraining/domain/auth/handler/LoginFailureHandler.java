package site.board.boardtraining.domain.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import site.board.boardtraining.global.exception.ErrorCode;
import site.board.boardtraining.global.response.error.ErrorApiResponse;

import java.io.IOException;

import static site.board.boardtraining.domain.auth.exception.AuthErrorCode.INVALID_CREDENTIALS;

@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        log.error("LoginFailureHandler.onAuthenticationFailure");
        setResponse(
                response,
                INVALID_CREDENTIALS
        );
    }

    private void setResponse(
            HttpServletResponse response,
            ErrorCode errorCode
    ) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(
                ErrorApiResponse.of(errorCode).convertResponseToJson()
        );
    }
}