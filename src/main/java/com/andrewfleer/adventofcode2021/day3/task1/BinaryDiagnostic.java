package com.andrewfleer.adventofcode2021.day3.task1;

import com.andrewfleer.adventofcode2021.day3.DiagnosticReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Scanner;

@Service
public class BinaryDiagnostic {

    @Autowired
    private ResourceLoader resourceLoader;

    public int doTask() {
        List<Integer> indexSums = new ArrayList<>();
        try {
            int lines = 0;
            Resource resource = resourceLoader.getResource("classpath:day3Input/task.txt");
            File inputFile = resource.getFile();
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                lines++;
                String binaryString = scanner.nextLine();

                char[] charArray = binaryString.toCharArray();

                for (int i = 0; i < charArray.length; i++) {
                    Integer intValue = Integer.parseInt(String.valueOf(charArray[i]));
                    if (indexSums.size() == i) {
                        indexSums.add(intValue);
                    } else {
                        indexSums.set(i, indexSums.get(i) + intValue);
                    }
                }
            }

            DiagnosticReport diagnosticReport = getDiagonsticReport(indexSums, lines);
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
        int gamma = Integer.parseInt(diagnosticReport.getGamma(), 2);
        int epsilon = Integer.parseInt(diagnosticReport.getEpsilon(), 2);

        return gamma * epsilon;
    }

    private DiagnosticReport getDiagonsticReport(List<Integer> indexSums, int numberOfLines) {
        DiagnosticReport diagnosticReport = new DiagnosticReport();
        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();


        for (Integer indexSum : indexSums) {
            int binary = 0;
            if (indexSum > numberOfLines / 2) {
                binary = 1;
            }
            gamma.append(binary);
            epsilon.append(1 - binary);
        }

        diagnosticReport.setEpsilon(epsilon.toString());
        diagnosticReport.setGamma(gamma.toString());

        return diagnosticReport;
    }
}
