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

@Log
@RestController
@RequestMapping("/oauth2")
public class OAuth2ManagementController {

    @Autowired
    MyAuthService authService;

    @RequestMapping("/findValid")
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