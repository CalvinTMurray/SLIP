package main;

import dataAccessLayer.DAO;
import di.configuration.DIConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import statistics.SessionStatisticsManager;

@ComponentScan({"main", "receivedAppData", "controllers", "integrationOne", "receivedAppData", "di.configuration", "dataAccessLayer", "statistics"})
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(DIConfiguration.class);
        ctx.getBean(DAO.class);

    	Thread statisticsThread = new Thread (SessionStatisticsManager.getInstance());
    	statisticsThread.start();
    	
        SpringApplication.run(Application.class, args);
        
    }
}