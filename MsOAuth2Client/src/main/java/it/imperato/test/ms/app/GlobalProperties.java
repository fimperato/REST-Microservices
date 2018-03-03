package it.imperato.test.ms.app;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@PropertySource("application.properties")
public class GlobalProperties {

    @Value("${oauth2.provider.facebook.active}")
    @Getter @Setter
    private String serverPort;

    @Value("${oauth2.provider.facebook.active}")
    @Getter @Setter
    private Boolean oauth2ProviderFacebookActive;

    @Value("${oauth2.provider.google.active}")
    @Getter @Setter
    private Boolean oauth2ProviderGoogleActive;

}
