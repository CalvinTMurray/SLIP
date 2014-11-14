package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import statistics.StatisticsThread;

@ComponentScan({"hello", "test", "di.configuration", "dataAccessLayer"})
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        
        Thread statisticsThread = new Thread (StatisticsThread.getInstance());
        statisticsThread.start();
    }
}
