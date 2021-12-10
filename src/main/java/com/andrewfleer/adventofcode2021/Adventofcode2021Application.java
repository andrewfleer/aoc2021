package com.andrewfleer.adventofcode2021;

import com.andrewfleer.adventofcode2021.day6.task1.LanternFishCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Adventofcode2021Application {

    @Autowired
    LanternFishCounter task;

    public static void main(String[] args) {
        SpringApplication.run(Adventofcode2021Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runTask() {
        System.out.println("The answer for this task is: " + task.doTask());
    }

}
