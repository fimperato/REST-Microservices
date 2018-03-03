package it.imperato.test.ms.controllers.google;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.imperato.test.ms.app.ProviderGlobalProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Endpoints:
 *
 * http://localhost:8088/fb/getResourceController
 *
 * http://localhost:8088/fb/msOAuth2AccessToken
 *
 *
 */
@RestController
@RequestMapping("/google")
public class GoogleResourceController {

    private static final Logger log = LoggerFactory.getLogger(GoogleResourceController.class);

    @Autowired
    @Qualifier(value = "googleRestTemplate")
    OAuth2RestTemplate googleRestTemplate;

    @RequestMapping("/getResourceController")
    public String getResourceController(){
        return "getResource (Google) controller ready.";
    }

    @RequestMapping("/msOAuth2AccessToken")
    public String msOAuth2AccessToken() {
        OAuth2AccessToken accessToken = googleRestTemplate.getAccessToken();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String accessTokenGson = gson.toJson(accessToken);
        log.info(accessTokenGson);
        return "MsOAuth2AccessToken (Google) ready with access_token: "+accessTokenGson;
    }

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

}
