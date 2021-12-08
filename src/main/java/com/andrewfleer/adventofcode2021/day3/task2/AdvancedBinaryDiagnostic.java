package com.andrewfleer.adventofcode2021.day3.task2;

import com.andrewfleer.adventofcode2021.day3.DiagnosticReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
public class AdvancedBinaryDiagnostic {

    @Autowired
    private ResourceLoader resourceLoader;

    public int doTask() {
        List<String> binaryStrings = new ArrayList<>();
        try {
            int lines = 0;
            Resource resource = resourceLoader.getResource("classpath:day3Input/task.txt");
            File inputFile = resource.getFile();
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                lines++;
                String binaryString = scanner.nextLine();

                binaryStrings.add(binaryString);
            }

            DiagnosticReport diagnosticReport = getDiagonsticReport(binaryStrings);
            return calculateReport(diagnosticReport);
        } catch (FileNotFoundException fnfe) {
            System.out.println("Can't find file");
            fnfe.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private int calculateReport(DiagnosticReport diagnosticReport) {
        int oxygen = Integer.parseInt(diagnosticReport.getOxygen(), 2);
        int co2 = Integer.parseInt(diagnosticReport.getCo2(), 2);

        return oxygen * co2;
    }

    private DiagnosticReport getDiagonsticReport(List<String> binaryStrings) {
        DiagnosticReport diagnosticReport = new DiagnosticReport();


        // Sort the strings
        Collections.sort(binaryStrings);

        diagnosticReport.setOxygen(getOxygen(binaryStrings));
        diagnosticReport.setCo2(getCo2(binaryStrings));

        return diagnosticReport;
    }

    private String getOxygen(List<String> binaryStrings) {

        return findRemainingString(binaryStrings, SearchType.MAJORITY);
    }

    private String getCo2(List<String> binaryStrings) {

        return findRemainingString(binaryStrings, SearchType.MINORITY);
    }

    String findRemainingString(List<String> strings, SearchType searchType) {
        int indexToCheck = 0;
        char charToCheck = '1';
        while (strings.size() > 1) {
            int midpoint = strings.size() / 2;
            int positionOfFirstChar = getPositionOfFirstChar(strings, indexToCheck, charToCheck);

            // If we have a majority of
            if (searchType == SearchType.MAJORITY) {
                if (positionOfFirstChar < midpoint + 1) {
                    strings = strings.subList(positionOfFirstChar, strings.size());
                } else {
                    strings = strings.subList(0, positionOfFirstChar);
                }
            } else {
                if (positionOfFirstChar >= midpoint + 1) {
                    strings = strings.subList(positionOfFirstChar, strings.size());
                } else {
                    strings = strings.subList(0, positionOfFirstChar);
                }
            }

            indexToCheck++;
        }

        return strings.get(0);

    }

    private int getPositionOfFirstChar(List<String> binaryStrings, int indexToCheck, char charToCheck) {
        int position = 0;
        for (String binaryString : binaryStrings) {
            if (binaryString.charAt(indexToCheck) == charToCheck) {
                break;
            }

            position++;
        }

        return position;
    }

    String flipOxygenReading(String oxygen) {
        StringBuilder flipped = new StringBuilder();

        for (char bitchar : oxygen.toCharArray()) {
            int bit = Integer.parseInt(String.valueOf(bitchar));
            flipped.append(1 - bit);
        }

        return flipped.toString();
    }
}
