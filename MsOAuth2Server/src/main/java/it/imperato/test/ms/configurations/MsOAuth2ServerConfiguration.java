package it.imperato.test.ms.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application-oauth2-default.properties")
@PropertySource("file:${MS_2018_CONF}/application-oauth2.properties") // Set MS_2018_CONF in env. variables
public class MsOAuth2ServerConfiguration {


}
