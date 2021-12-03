package com.andrewfleer.adventofcode2021.day2.task2;

import com.andrewfleer.adventofcode2021.day2.Direction;
import com.andrewfleer.adventofcode2021.day2.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class AimFinder {

    @Autowired
    private ResourceLoader resourceLoader;

    public int doTask() {
        List<Position> positions = new ArrayList<>();
        try {
            Resource resource = resourceLoader.getResource("classpath:day2Input/directions.txt");
            File inputFile = resource.getFile();
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String direction = scanner.nextLine();
                String[] splitString = direction.split(" ");

                Position position = new Position();
                position.setDirection(Direction.valueOf(splitString[0].toUpperCase()));
                position.setValue(Integer.parseInt(splitString[1]));

                positions.add(position);
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Can't find file");
            fnfe.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!CollectionUtils.isEmpty(positions)) {
            return calculatePosition(positions);
        }

        return 0;
    }

    private int calculatePosition(List<Position> positions) {
        int horizontal = 0;
        int vertical = 0;
        int aim = 0;

        for (Position position : positions) {
            switch (position.getDirection()) {
                case UP:
                    aim -= position.getValue();
                    break;
                case DOWN:
                    aim += position.getValue();
                    break;
                case FORWARD:
                    horizontal += position.getValue();
                    vertical += aim * position.getValue();
            }
        }

        return horizontal * vertical;
    }
}
