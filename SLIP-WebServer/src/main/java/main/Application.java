package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import statistics.StatisticsThread;

@ComponentScan({"main", "receivedAppData", "controllers", "integrationOne", "receivedAppData", "di.configuration", "dataAccessLayer", "statistics"})
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        
        Thread statisticsThread = new Thread (StatisticsThread.getInstance());
        statisticsThread.start();
    }
}