package it.imperato.test.ms;

import it.imperato.test.ms.model.entities.JwtTokenInfo;
import it.imperato.test.ms.repository.JwtTokenRepository;
import it.imperato.test.ms.utils.ConstantsApp;
import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
// @EnableAutoConfiguration
// @ComponentScan(basePackageClasses = {MyRestController.class, MyAuthService.class, EncryptionUtils.class})
public class MsElqClientApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MsElqClientApplication.class);

    @Autowired
    JwtTokenRepository jwtTokenRepository;

    public static void main(String[] args){
        log.info("Spring Boot 2 Application started.");
        SpringApplication.run(MsElqClientApplication.class, args);
    }

    public void test(String... strings) throws Exception {
    }

    @Bean
    public BasicTextEncryptor myTextEncryptor(){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(ConstantsApp.SIGNIN_KEY);
        return textEncryptor;
    }

    @Override
    public void run(String... args) throws Exception {

        JwtTokenInfo firstTokenRow = jwtTokenRepository
                .findFirstByValidAndSystem(true, ConstantsApp.SISTEMA_MS_APP_CLIENT);
        log.info("Jwt first valid row available: "
                + (firstTokenRow!=null?firstTokenRow.toString():"N.D."));

    }
}