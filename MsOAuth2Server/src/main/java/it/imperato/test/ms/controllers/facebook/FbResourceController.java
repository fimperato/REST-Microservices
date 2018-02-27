package it.imperato.test.ms.controllers.facebook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.imperato.test.ms.app.FbGlobalProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * http://localhost:8088/fb/useConnection
 *
 *
 */
@RestController
@RequestMapping("/fb")
public class FbResourceController {

    private static final Logger log = LoggerFactory.getLogger(FbResourceController.class);

    @Autowired
    FbGlobalProperties globalProperties;

    @Autowired
    OAuth2RestTemplate fbRestTemplate;

    @RequestMapping("/getResourceController")
    public String getResourceController(){
        return "getResource (FB) controller ready.";
    }

    @RequestMapping("/msOAuth2AccessToken")
    public String msOAuth2AccessToken() {
        OAuth2AccessToken accessToken = fbRestTemplate.getAccessToken();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String accessTokenGson = gson.toJson(accessToken);
        log.info(accessTokenGson);
        return "MsOAuth2AccessToken ready with access_token: "+accessTokenGson;
    }

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

    @RequestMapping(value = "/useConnection", method = GET)
    public String useConnection() {
        Connection<Facebook> connection = null;
        Facebook facebook = null;
        /* if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        } */

        FacebookConnectionFactory fcf = new FacebookConnectionFactory(
                globalProperties.getClientID(),
                globalProperties.getClientSecret());
        //this is the important bit
        fcf.setScope("public_profile,email");

        OAuth2Operations oauthOperations = fcf.getOAuthOperations();
        try {
            AccessGrant accessGrant = new AccessGrant(fbRestTemplate.getAccessToken().getValue(),
                    null, null, Long.valueOf(fbRestTemplate.getAccessToken().getExpiresIn()));
            connection = fcf.createConnection(accessGrant);
            facebook = connection != null ? connection.getApi() : null;

            // (#12) bio field is deprecated for versions v2.8 and higher
            // 2.0.3.RELEASE of spring-social-facebook seems to be not compatible with v2.8 Facebook API version
            UserProfile profile = connection.fetchUserProfile();
        }
        catch (Exception e){
            log.error("ERROR " + e.getMessage());
        }

        String [] fields = { "id","name","birthday","email","location","hometown","gender","first_name","last_name","picture"};
        User user = facebook.fetchObject("me", User.class, fields);
        String name=user.getName();
        String birthday=user.getBirthday();
        String email=user.getEmail();
        String gender=user.getGender();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userGson = gson.toJson(user);
        return userGson;
    }

}
