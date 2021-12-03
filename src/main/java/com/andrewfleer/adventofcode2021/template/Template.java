package com.andrewfleer.adventofcode2021.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

@Service
public class Template {

    @Autowired
    private ResourceLoader resourceLoader;

    public int method() {
        try {
            Resource resource = resourceLoader.getResource("classpath:folder/fileName.txt");
            File inputFile = resource.getFile();
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {

            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Can't find file");
            fnfe.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
