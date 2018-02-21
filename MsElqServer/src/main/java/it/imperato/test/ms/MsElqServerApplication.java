package it.imperato.test.ms;

import it.imperato.test.ms.model.entities.Domain;
import it.imperato.test.ms.repository.DomainRepository;
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
public class MsElqServerApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MsElqServerApplication.class);

    @Autowired
    private DomainRepository domainRepository;

    public static void main(String[] args){
        log.info("Spring Boot 2 Application started.");
        SpringApplication.run(MsElqServerApplication.class, args);
    }

    @Bean
    public BasicTextEncryptor myTextEncryptor(){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(ConstantsApp.SIGNIN_KEY);
        return textEncryptor;
    }

    @Override
    public void run(String... args) throws Exception {

        // domainRepository.deleteAll();

        Domain firstByDom = domainRepository.findFirstByDomain("imperato_domain");
        System.out.println(firstByDom);

    }
}