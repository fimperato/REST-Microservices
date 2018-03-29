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

    @Value("${local.ip}")
    @Getter @Setter
    private String localIp;

    @Value("${docker.ip}")
    @Getter @Setter
    private String dockerIp;

    public static final String RESOURCE_SERVER_BASE_URL = "http://localhost:8085/api";
    public static final String AUTH_SERVER_BASE_URL = "http://localhost:8087";

    public String getResourceServerBaseUrl() {
        if(dockerIp!=null && !dockerIp.isEmpty()) {
            return RESOURCE_SERVER_BASE_URL.replace("localhost",dockerIp);
        }
        return RESOURCE_SERVER_BASE_URL;
    }

    public String getAuthServerBaseUrl() {
        if(dockerIp!=null && !dockerIp.isEmpty()) {
            return RESOURCE_SERVER_BASE_URL.replace("localhost",dockerIp);
        }
        return AUTH_SERVER_BASE_URL;
    }
}
