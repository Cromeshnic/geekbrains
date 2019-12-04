package ru.dsi.geekbrains.testproject;


import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApp {
    public static void main(String[] args) throws Exception{
        SpringApplication.run(MainApp.class, args);
    }

    @Bean(name="sessionFactory")
    public SessionFactory getSessionFactory(){
        return new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }
}

