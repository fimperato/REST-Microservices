package it.imperato.test.ms.configurations.facebook;

import it.imperato.test.ms.utils.ConstantsApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

import java.util.Arrays;

@Configuration
public class MsFbOAuth2ServerConfiguration {

    @Autowired
    FbGlobalProperties fbGlobalProperties;

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @Autowired
    AuthorizationCodeResourceDetails facebook;

    @Autowired
    GlobalProperties globalProperties;

    @Bean
    public OAuth2ProtectedResourceDetails fbResourceDetails() {
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setId("facebook");
        details.setClientId(fbGlobalProperties.getClientID());
        details.setClientSecret(fbGlobalProperties.getClientSecret());
        details.setAccessTokenUri(fbGlobalProperties.getAccessTokenUri());
        details.setUserAuthorizationUri(fbGlobalProperties.getUserAuthorizationUri());
        details.setTokenName(fbGlobalProperties.getTokenName());
        details.setScope(Arrays.asList("identity"));
        details.setPreEstablishedRedirectUri("http://localhost"
                + ":" + globalProperties.getServerPort() + ConstantsApp.FB_LOGIN_URI);
        details.setUseCurrentUri(false);
        return details;
    }

    @Bean
    public OAuth2RestTemplate fbRestTemplate() {
        OAuth2ClientAuthenticationProcessingFilter facebookFilter =
                new OAuth2ClientAuthenticationProcessingFilter(ConstantsApp.FB_LOGIN_URI);
        OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook, oauth2ClientContext);
        return facebookTemplate;
    }

}
