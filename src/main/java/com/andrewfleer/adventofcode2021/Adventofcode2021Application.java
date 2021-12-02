package com.andrewfleer.adventofcode2021;

import com.andrewfleer.adventofcode2021.day1.task1.DepthFinder;
import com.andrewfleer.adventofcode2021.day1.task2.DepthWindowFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Adventofcode2021Application {

    @Autowired
    DepthWindowFinder depthWindowFinder;

    public static void main(String[] args) {
        SpringApplication.run(Adventofcode2021Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runTask() {
        System.out.println("The answer for this task is: " + depthWindowFinder.findDepthWindow());
    }

}
