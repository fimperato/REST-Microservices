package it.imperato.test.ms.app;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@PropertySource("application-oauth2-default.properties")
@PropertySource("file:${MS_2018_CONF}/application-oauth2.properties") // Set MS_2018_CONF in env. variables
public class FbGlobalProperties {

    @Value("${facebook.client.clientId}")
    @Getter @Setter
    private String clientID;

    @Value("${facebook.client.clientSecret}")
    @Getter @Setter
    private String clientSecret;

    @Value("${facebook.client.accessTokenUri}")
    @Getter @Setter
    private String accessTokenUri;

    @Value("${facebook.client.userAuthorizationUri}")
    @Getter @Setter
    private String userAuthorizationUri;

    @Value("${facebook.client.tokenName}")
    @Getter @Setter
    private String tokenName;

}
