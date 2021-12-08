package com.andrewfleer.adventofcode2021;

import com.andrewfleer.adventofcode2021.day1.task1.DepthFinder;
import com.andrewfleer.adventofcode2021.day1.task2.DepthWindowFinder;
import com.andrewfleer.adventofcode2021.day2.task1.PositionFinder;
import com.andrewfleer.adventofcode2021.day2.task2.AimFinder;
import com.andrewfleer.adventofcode2021.day3.task1.BinaryDiagnostic;
import com.andrewfleer.adventofcode2021.day3.task2.AdvancedBinaryDiagnostic;
import com.andrewfleer.adventofcode2021.day4.task1.GiantSquidBingo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Adventofcode2021Application {

    @Autowired
    GiantSquidBingo task;

    public static void main(String[] args) {
        SpringApplication.run(Adventofcode2021Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runTask() {
        System.out.println("The answer for this task is: " + task.doTask());
    }

}
