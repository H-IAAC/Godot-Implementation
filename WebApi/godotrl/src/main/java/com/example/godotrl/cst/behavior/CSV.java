package com.example.godotrl.cst.behavior;

import java.io.IOException;
import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Locale;

public class CSV {
    private PrintWriter writer;
    private String localPathToFile;
    private String fileName;
    private Boolean append;

    public CSV(
            String localPathToFile,
            String fileName,
            Boolean appendContentToExistingFile,
            Boolean initReward
    ) {
        this.localPathToFile = localPathToFile;
        this.fileName = fileName;
        this.append = appendContentToExistingFile;
        initializeCallback(initReward);
    }

    public void recordNewEpisode(
            Long episode,
            Domain reward,
            Double epsilon
    ) {
        String line = String.format(
                Locale.US,
                "%d, %.2f, %.2f",
                episode,
                reward.doubleValue(),
                epsilon
        );
        this.writer.println(line);
        this.writer.flush();
    }

    public void recordNewEpisode(
            Integer episode,
            Domain reward,
            Double epsilon
    ) {
        String line = String.format(
                Locale.US,
                "%d, %.2f, %.2f",
                episode,
                reward.doubleValue(),
                episode
        );
        this.writer.println(line);
        this.writer.flush();
    }

    public void finishCallback() {
        try {
            this.writer.flush();
            this.writer.close();
        }
        catch (Exception e) {
            System.out.println("Error to finish CSV callback");
            e.printStackTrace();
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

    private void initializeCallback(boolean initReward) {

        createFile(this.localPathToFile, this.fileName);

        try (FileReader fr = new FileReader(this.localPathToFile + this.fileName);
             BufferedReader br = new BufferedReader(fr);)
        {
            FileWriter fw = new FileWriter(this.localPathToFile + this.fileName, this.append);
            BufferedWriter bw = new BufferedWriter(fw);
            this.writer = new PrintWriter(bw);
            if(initReward && br.readLine() == null) {
                this.writer.println("episode, reward, epsilon");
            }

            this.writer.flush();

        } catch (IOException e) {
            System.out.println("Could not initialize CSV callback");
            e.printStackTrace();
        }
    }
}