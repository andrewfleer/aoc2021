package com.andrewfleer.adventofcode2021.day1.task2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class DepthWindowFinder {

    @Autowired
    ResourceLoader resourceLoader;

    public int doTask() {
        int numberOfIncreases = 0;
        int windowSize = 3;
        List<Integer> depths = new ArrayList<>();
        List<Integer> windowSums = new ArrayList<>();

        try {
            Resource resource = resourceLoader.getResource("classpath:day1Input/task.txt");
            File inputFile = resource.getFile();
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                Integer nextDepth = scanner.nextInt();
                depths.add(nextDepth);
            }

            for (int i = 0; i < depths.size(); i++) {
                int currentWindowSum = 0;
                int numberOfValues = 0;
                for (int j = 0; j < windowSize; j++) {
                    if ((i + j) >= depths.size()) {
                        break;
                    }
                    numberOfValues = j + 1;
                    currentWindowSum += depths.get(i + j);
                }
                if (numberOfValues > 3) {
                    break;
                }
                windowSums.add(currentWindowSum);
            }

            Integer currentSum = null;
            for (int sum : windowSums) {
                if (currentSum != null && sum > currentSum) {
                    numberOfIncreases++;
                }

                currentSum = sum;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return numberOfIncreases;
    }
}
