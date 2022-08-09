package com.example.godotrl.cst.behavior;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Set;

public class WeightsLFA {
    private PrintWriter writer;
    private String localPathToFile;
    private String fileName;
    private LinkedHashMap<String, Double> weights;

    public WeightsLFA(
            String localPathToFile,
            String fileName
    ) {
        this.localPathToFile = localPathToFile;
        this.fileName = fileName;
        initializeCallback();
    }

    public void recordNewEpisode( LinkedHashMap<String, Double> weights ) {
        Set<String> features = weights.keySet();

        for (String f : features) {
            Double value = weights.get(f);
            String line = String.format(
                    Locale.US,
                    "%s, %f", f, value
            );
            this.writer.println(line);
            this.writer.flush();
        }
    }

    public static void createFile(String filePath) {

        System.out.printf("file path: %s.", filePath );

        try {
            File file = new File( filePath );
            if (file.createNewFile()) {
                System.out.printf("file %s created.", filePath );
            }
            else
                System.out.printf("file %s already exists.", filePath );
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFile(String localPathToFile, String fileName) {
        try {
            File file = new File( localPathToFile + fileName );
            if (file.createNewFile()) {
                System.out.printf("file %s created at %s.", fileName,localPathToFile);
            }
            else
                System.out.printf("file %s already exists at %s.", fileName, localPathToFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeCallback() {

        this.createFile(this.localPathToFile, this.fileName);

        try (FileReader fr = new FileReader(this.localPathToFile + this.fileName);
             BufferedReader br = new BufferedReader(fr);)
        {
            FileWriter fw = new FileWriter(this.localPathToFile + this.fileName);
            BufferedWriter bw = new BufferedWriter(fw);

            this.writer = new PrintWriter(bw);
            this.writer.flush();

        } catch (IOException e) {
            System.out.println("Could not initialize CSV callback");
            e.printStackTrace();
        }
    }
}
