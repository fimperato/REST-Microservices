package it.imperato.test.ms.app;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@PropertySource("application-oauth2-default.properties")
@PropertySource("file:${MS_2018_CONF}/application-oauth2.properties") // Set MS_2018_CONF in env. variables
public class GoogleGlobalProperties {

    @Value("${google.client.clientId}")
    @Getter @Setter
    private String clientID;

    @Value("${google.client.clientSecret}")
    @Getter @Setter
    private String clientSecret;

    @Value("${google.client.accessTokenUri}")
    @Getter @Setter
    private String accessTokenUri;

    @Value("${google.client.userAuthorizationUri}")
    @Getter @Setter
    private String userAuthorizationUri;

    @Value("${google.client.clientAuthenticationScheme}")
    @Getter @Setter
    private String clientAuthenticationScheme;

    @Value("${google.client.scope}")
    @Getter @Setter
    private String scope;


    @Value("${google.resource.userInfoUri}")
    @Getter @Setter
    private String userInfoUri;

    @Value("${google.resource.preferTokenInfo}")
    @Getter @Setter
    private String preferTokenInfo;
}
