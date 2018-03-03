package it.imperato.test.ms.configurations.google;

import it.imperato.test.ms.app.GlobalProperties;
import it.imperato.test.ms.app.ProviderGlobalProperties;
import it.imperato.test.ms.utils.ConstantsApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

import javax.annotation.Resource;
import java.util.Arrays;

@Configuration
public class MsGoogleOAuth2ServerConfiguration {

    @Autowired
    ProviderGlobalProperties providerGlobalProperties;

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @Autowired
    AuthorizationCodeResourceDetails googleResourceDetails;

    @Autowired
    GlobalProperties globalProperties;

    @Bean
    public OAuth2ProtectedResourceDetails googleResourceDetails() {
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setId("facebook");
        details.setClientId(providerGlobalProperties.getGClientID());
        details.setClientSecret(providerGlobalProperties.getGClientSecret());
        details.setAccessTokenUri(providerGlobalProperties.getGAccessTokenUri());
        details.setUserAuthorizationUri(providerGlobalProperties.getGUserAuthorizationUri());
        details.setTokenName(providerGlobalProperties.getGTokenName());
        details.setScope(Arrays.asList("identity"));
        //details.setPreEstablishedRedirectUri("http://localhost" + ":" + globalProperties.getServerPort() + ConstantsApp.GOOGLE_LOGIN_URI);
        details.setPreEstablishedRedirectUri("/oauth/callback");
        details.setUseCurrentUri(false);
        return details;
    }

    @Bean(name = "googleRestTemplate")
    public OAuth2RestTemplate googleRestTemplate() {
        OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(googleResourceDetails, oauth2ClientContext);
        return googleTemplate;
    }

}
