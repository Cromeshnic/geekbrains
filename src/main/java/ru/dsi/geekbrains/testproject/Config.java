package ru.dsi.geekbrains.testproject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
@ComponentScan(basePackages = {"ru.dsi.geekbrains.testproject"})
public class Config {
    @Bean
    public EntityManagerFactory entityManagerFactory(){
        return new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }
}
