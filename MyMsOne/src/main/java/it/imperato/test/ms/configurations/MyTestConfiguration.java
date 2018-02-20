package it.imperato.test.ms.configurations;

import it.imperato.test.ms.model.pojo.Company;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyTestConfiguration {

    @Bean
    public Company italianCompany() {
        return new Company("some ItalianCompany name", "Rome");
    }

    @Bean(name = "Barilla")
    public Company getBarillaCompany() {
        return new Company("Barilla company", "Parma");
    }
}
