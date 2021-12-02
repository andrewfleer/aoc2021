package com.andrewfleer.adventofcode2021.day1.task1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

@Service
public class DepthFinder {

    @Autowired
    private ResourceLoader resourceLoader;

    public int findDepth() {
        Integer currentDepth = null;
        int numberOfIncreases = 0;
        try {
            Resource resource = resourceLoader.getResource("classpath:day1Input/task.txt");
            File inputFile = resource.getFile();
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                Integer nextDepth = scanner.nextInt();
                if (currentDepth != null && nextDepth.compareTo(currentDepth) > 0) {
                    numberOfIncreases++;
                }

                currentDepth = nextDepth;
            }

        } catch (FileNotFoundException fnfe) {
            System.out.println("Can't find file");
            fnfe.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return numberOfIncreases;
    }
}
