package com.andrewfleer.adventofcode2021.day5.task1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class HydrothermalVents {
    @Autowired
    private ResourceLoader resourceLoader;

    public int doTask() {
        try {
            List<String[]> paths = new ArrayList<>();
            Resource resource = resourceLoader.getResource("classpath:day5Input/task.txt");
            File inputFile = resource.getFile();
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String path = scanner.nextLine();
                String[] startAndEnd = path.split(" -> ");
                paths.add(startAndEnd);
            }

            int[][] map = buildMap(paths);

            populateMap(map, paths);

            return findCrossingPoints(map);
        } catch (FileNotFoundException fnfe) {
            System.out.println("Can't find file");
            fnfe.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private int findCrossingPoints(int[][] map) {
        int crossingPoints = 0;
        for (int[] ints : map) {
            for (int anInt : ints) {
                if (anInt > 1) {
                    crossingPoints++;
                }
            }
        }

        return crossingPoints;
    }

    private void populateMap(int[][] map, List<String[]> paths) {

        for (String[] path : paths) {
            String[] from = path[0].split(",");
            String[] to = path[1].split(",");

            int fromX = Integer.parseInt(from[0]);
            int fromY = Integer.parseInt(from[1]);
            int toX = Integer.parseInt(to[0]);
            int toY = Integer.parseInt(to[1]);

            if (fromX == toX) { // Vertical Stuff
                int smallerY = Math.min(fromY, toY);
                int biggerY = Math.max(fromY, toY);
                for (int i = smallerY; i <= biggerY; i++) {
                    int currentVal = map[i][fromX];
                    map[i][fromX] = currentVal + 1;
                }
            } else if (fromY == toY) { // Horizontal Stuff
                int smallerX = Math.min(fromX, toX);
                int biggerX = Math.max(fromX, toX);
                for (int i = smallerX; i <= biggerX; i++) {
                    int currentVal = map[fromY][i];
                    map[fromY][i] = currentVal + 1;
                }

            }
        }

    }

    private int[][] buildMap(List<String[]> paths) {
        int maxX = 0;
        int maxY = 0;

        for (String[] path : paths) {
            for (String s : path) {
                String[] xAndY = s.split(",");
                int x = Integer.parseInt(xAndY[0]);
                int y = Integer.parseInt(xAndY[1]);

                if (x > maxX)
                    maxX = x;

                if (y > maxY)
                    maxY = y;
            }
        }

        return new int[maxX + 1][maxY + 1];
    }
}

