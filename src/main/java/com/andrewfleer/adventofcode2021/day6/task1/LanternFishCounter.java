package com.andrewfleer.adventofcode2021.day6.task1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

@Service
public class LanternFishCounter {

    @Autowired
    private ResourceLoader resourceLoader;

    public int doTask() {
        try {
            String[] startingFish = new String[0]; // idk, just make an array.
            Resource resource = resourceLoader.getResource("classpath:day6Input/task.txt");
            File inputFile = resource.getFile();
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                startingFish = line.split(",");
            }

            int[] fish = seedFish(startingFish);

            return simulateLife(fish, 80);
        } catch (FileNotFoundException fnfe) {
            System.out.println("Can't find file");
            fnfe.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private int simulateLife(int[] fish, int days) {
        for (int i = 0; i < days; i++) {
            int babyFish = fish[0]; // These fish made babies
            int fishThatJustHadBirth = fish[0]; // These fish are ready to start over
            for (int fishAge = 1; fishAge < fish.length; fishAge++) {
                fish[fishAge - 1] = fish[fishAge]; // These fish move a day down the "ticker";
            }

            fish[8] = babyFish; // we made babies!
            fish[6] = fish[6] + fishThatJustHadBirth; // add these fish to the pregnancy pool
        }

        // Add all the fish together
        int totalFish = 0;
        for (int j : fish) {
            totalFish += j;
        }

        return totalFish;
    }

    private int[] seedFish(String[] startingFish) {
        int[] fish = new int[9];

        for (String s : startingFish) {
            int fishAge = Integer.parseInt(s);

            fish[fishAge] = fish[fishAge] + 1;
        }

        return fish;
    }
}
