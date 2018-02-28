package it.imperato.test.ms.configurations.google;

import it.imperato.test.ms.app.GlobalProperties;
import it.imperato.test.ms.utils.ConstantsApp;
import lombok.extern.java.Log;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log
@Component
public class GoogleBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private GlobalProperties globalProperties = new GlobalProperties();

    @Override
    public void commence
            (HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException, ServletException {
        //response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
        //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //PrintWriter writer = response.getWriter();
        //writer.println("HTTP Status 401 - " + authEx.getMessage());
        log.info("GoogleBasicAuthenticationEntryPoint commence method called.");

        //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //response.sendRedirect(ConstantsApp.GOOGLE_LOGIN_URI);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("Francesco");
        super.afterPropertiesSet();
    }

}