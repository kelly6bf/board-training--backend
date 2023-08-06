package site.board.boardtraining.auth.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.POST;

@Slf4j
public class JsonIdPwAuthenticationFilter
        extends AbstractAuthenticationProcessingFilter {

    public JsonIdPwAuthenticationFilter(
            AuthenticationManager authenticationManager,
            RequestMatcher requiresAuthenticationRequestMatcher
    ) {
        super(requiresAuthenticationRequestMatcher);
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException,
            IOException,
            ServletException {

        validateHttpRequestMethod(request);

        Map<String, Object> parseJsonRequest = parseJsonRequest(request);

        String personalId = (String) parseJsonRequest.get("personalId");
        String password = (String) parseJsonRequest.get("password");

        UsernamePasswordAuthenticationToken authenticationRequest = new UsernamePasswordAuthenticationToken(personalId, password);

        return super.getAuthenticationManager().authenticate(authenticationRequest);
    }

    private void validateHttpRequestMethod(HttpServletRequest request) {
        if (!request.getMethod().equals(POST.toString())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
    }

    private Map<String, Object> parseJsonRequest(HttpServletRequest request) throws IOException {
        String body = request.getReader().lines().collect(Collectors.joining());
        GsonJsonParser gsonJsonParser = new GsonJsonParser();

        return gsonJsonParser.parseMap(body);
    }
}