package it.imperato.test.ms;

import it.imperato.test.ms.model.entities.Activity;
import it.imperato.test.ms.model.entities.Domain;
import it.imperato.test.ms.repository.DomainRepository;
import it.imperato.test.ms.services.ActivityService;
import it.imperato.test.ms.utils.ConstantsApp;
import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
// @EnableAutoConfiguration
// @ComponentScan(basePackageClasses = {MyRestController.class, MyAuthService.class, EncryptionUtils.class})
public class MsElqResourceServerApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MsElqResourceServerApplication.class);

    @Autowired
    DomainRepository domainRepository;

    @Autowired
    ActivityService activityService;

    public static void main(String[] args){
        log.info("Spring Boot 2 Application started.");
        SpringApplication.run(MsElqResourceServerApplication.class, args);
    }

    @Bean
    public BasicTextEncryptor myTextEncryptor(){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(ConstantsApp.SIGNIN_KEY);
        return textEncryptor;
    }

    @Override
    public void run(String... args) throws Exception {

        Domain firstByDom = domainRepository.findFirstByDomain("imperato_domain");
        log.info(firstByDom!=null?firstByDom.toString():"N.D.");

        // AbstractApplicationContext context = new AnnotationConfigApplicationContext(MyApplicationConfig.class);
        // ActivityService carService = (ActivityService) context.getBean("activityService");

        // Rimuovo tutte le activity
        activityService.deleteAll();

        // Inserimenti di activity
        Activity activity1 = new Activity(null,
                "1519210423520","Completed","Landing Page","14",null);
        activityService.create(activity1);

        Activity activity2 = new Activity(null,
                "1519210423525","Received","Email","15",null);
        activityService.create(activity2);
        Activity activity3 = new Activity(null,
                "1519210423551","Open","Email","15",null);
        activityService.create(activity3);

        List<Activity> activities = activityService.findByContact("15");
        log.info("Contact-15 activities. "
                + (activities!=null?"Activities found: "+String.valueOf(activities.size()):"N.D.") );

        activities = activityService.findAll();
        log.info("All activities. "
                + (activities!=null?"Activities found: "+String.valueOf(activities.size()):"N.D.") );
    }
}