package it.imperato.test.ms.configurations.facebook;

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

    @Value("${server.port}")
    @Getter @Setter
    private String serverPort;

}
