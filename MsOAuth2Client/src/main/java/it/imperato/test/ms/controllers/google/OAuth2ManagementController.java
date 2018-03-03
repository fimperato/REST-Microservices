package it.imperato.test.ms.controllers.google;

import it.imperato.test.ms.app.ProviderGlobalProperties;
import it.imperato.test.ms.model.entities.OAuthInfo;
import it.imperato.test.ms.services.MyAuthService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;

@Log
@RestController
@RequestMapping("/oauth2")
public class OAuth2ManagementController {

    @Autowired
    @Qualifier(value = "googleRestTemplate")
    OAuth2RestTemplate googleRestTemplate;

    @Autowired
    MyAuthService authService;

    @RequestMapping("/callback")
    public String processOAuthCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OAuth2AccessToken oAuth2AccessToken = googleRestTemplate.getAccessToken();
        String accessToken = oAuth2AccessToken.getValue();
        OAuth2RefreshToken oAuth2RefreshToken = oAuth2AccessToken.getRefreshToken();
        String refreshToken = oAuth2RefreshToken!=null?oAuth2RefreshToken.getValue():"N.D.";
        Date expDate = oAuth2AccessToken.getExpiration();
        String tokenType = oAuth2AccessToken.getTokenType();

        String authorizationCode = request.getParameter("authorization_code");
        String msg = "";
        if(authorizationCode==null) {
            msg = "[Info by previous authorization] ";
        } else {
            msg = "[New authorization info] ";
        }
        msg += "access_token: " + accessToken + " refresh_token: " + refreshToken
                + " expiration_date: " + expDate + " token_type: " + tokenType;
        log.info(msg);

        try {
            // Memorizzo i dati sul client:
            authService.saveOrUpdateOAuth2TokenInfo(oAuth2AccessToken);
        } catch(Exception e) {
            log.log(Level.SEVERE, "ERRORE durante il salvataggio: impossibile salvare le informazioni di OAuth");
        }

        return msg;
    }

    @RequestMapping("/getInfo/findValid")
    public String findValid(@RequestHeader HttpHeaders httpHeaders) throws IOException {
        OAuthInfo oAuthInfo = authService.findValidToken();
        String msg = "[DB saved info] " +
                "access_token: " + oAuthInfo.getAccessToken() + " refresh_token: " + oAuthInfo.getRefreshToken()
                + " expiration_date: " + oAuthInfo.getAccessTokenExpirationDate() + " token_type: " + oAuthInfo.getTokenType();
        log.info(msg);
        // mem.
        return msg;
    }

}