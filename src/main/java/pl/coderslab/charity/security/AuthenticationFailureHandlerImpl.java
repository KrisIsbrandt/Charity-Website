package pl.coderslab.charity.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    private String targetUrl;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
                                        throws IOException   {

        handle(request, response, exception);
    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response,
                          Exception exception)
                          throws IOException {

        //Bad credentials
        if (exception instanceof BadCredentialsException) {
            targetUrl = "/login?error=bad_credentials";
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }


}

