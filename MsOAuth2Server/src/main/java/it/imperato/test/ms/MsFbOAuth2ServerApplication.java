package it.imperato.test.ms;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Log
@EnableOAuth2Client
@SpringBootApplication
public class MsFbOAuth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsFbOAuth2ServerApplication.class, args);
    }

}