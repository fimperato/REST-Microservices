package it.imperato.test.ms.controllers.google;

import it.imperato.test.ms.app.ProviderGlobalProperties;
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
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;

/**
 * Callback endpoint management:
 * <p>
 * [host]:[server.port]/oauth2/code/google
 */
@Log
@RestController
@RequestMapping("/oauth2")
public class OAuth2RequestController {

    @Autowired
    @Qualifier(value = "googleRestTemplate")
    OAuth2RestTemplate googleRestTemplate;

    @Autowired
    ProviderGlobalProperties providerGlobalProperties;

    @RequestMapping("/code/google")
    public void processOAuthCodeGoogle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationCode = request.getParameter("authorization_code");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("client_id", providerGlobalProperties.getGClientID());
        map.add("client_secret", providerGlobalProperties.getGClientSecret());
        //map.add("redirect_uri", "http://localhost:8088/oauth2/code/google"); // to complete
        map.add("grant_type", "authorization_code"); // to complete
        map.add("code", authorizationCode);

        HttpEntity<MultiValueMap<String, String>> tokenRequest =
                new HttpEntity<MultiValueMap<String, String>>(map, headers);

        //ResponseEntity<String> tokenResponse = googleRestTemplate.postForEntity("https://accounts.google.com/o/oauth2"+"/token", tokenRequest, String.class);
        ResponseEntity<String> tokenResponse = googleRestTemplate
                .postForEntity("https://www.googleapis.com/oauth2/v4"+"/token", tokenRequest, String.class);

        log.info(tokenResponse != null ? tokenResponse.toString() : "");
    }
}