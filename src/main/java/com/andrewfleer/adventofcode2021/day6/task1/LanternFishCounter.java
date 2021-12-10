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

    public long doTask() {
        try {
            String[] startingFish = new String[0]; // idk, just make an array.
            Resource resource = resourceLoader.getResource("classpath:day6Input/task.txt");
            File inputFile = resource.getFile();
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                startingFish = line.split(",");
            }

            long[] fish = seedFish(startingFish);

            return simulateLife(fish, 256);
        } catch (FileNotFoundException fnfe) {
            System.out.println("Can't find file");
            fnfe.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private long simulateLife(long[] fish, int days) {
        for (int i = 0; i < days; i++) {
            long babyFish = fish[0]; // These fish made babies
            long fishThatJustHadBirth = fish[0]; // These fish are ready to start over
            for (int fishAge = 1; fishAge < fish.length; fishAge++) {
                fish[fishAge - 1] = fish[fishAge]; // These fish move a day down the "ticker";
            }

            fish[8] = babyFish; // we made babies!
            fish[6] = fish[6] + fishThatJustHadBirth; // add these fish to the pregnancy pool
        }

        // Add all the fish together
        long totalFish = 0;
        for (long j : fish) {
            totalFish += j;
        }

        return totalFish;
    }

    private long[] seedFish(String[] startingFish) {
        long[] fish = new long[9];

        for (String s : startingFish) {
            int fishAge = Integer.parseInt(s);

            fish[fishAge] = fish[fishAge] + 1;
        }

        return fish;
    }
}
