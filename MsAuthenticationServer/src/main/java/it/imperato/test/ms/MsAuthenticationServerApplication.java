package it.imperato.test.ms;

import it.imperato.test.ms.utils.ConstantsApp;
import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MsAuthenticationServerApplication {

    private static final Logger log = LoggerFactory.getLogger(MsAuthenticationServerApplication.class);

    public static void main(String[] args){
        log.info("Spring Boot 2 Application started.");
        SpringApplication.run(MsAuthenticationServerApplication.class, args);
    }

    public void test(String... strings) throws Exception {
    }

    @Bean
    public BasicTextEncryptor myTextEncryptor(){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(ConstantsApp.SIGNIN_KEY);
        return textEncryptor;
    }

}