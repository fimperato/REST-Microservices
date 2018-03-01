package it.imperato.test.ms.configurations.google;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.imperato.test.ms.app.ProviderGlobalProperties;
import it.imperato.test.ms.utils.UrlTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;

@Component("customGoogleAuthenticationHandler")
public class CustomAuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    ProviderGlobalProperties providerGlobalProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        String authorizationCode = (String) request.getParameter("code");
        //DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY");
        String requestUrl = providerGlobalProperties.getGFilterCallbackPath();
        if( authorizationCode != null ) {
            requestUrl = UrlTool.addParamToURL(requestUrl, "authorization_code", authorizationCode, true);
            getRedirectStrategy().sendRedirect(request, response, requestUrl);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
