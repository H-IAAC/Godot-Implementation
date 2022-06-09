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
            Boolean appendContentToExistingFile
    ) {
        this.localPathToFile = localPathToFile;
        this.fileName = fileName;
        this.append = appendContentToExistingFile;
        initializeCallback();
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

    private void initializeCallback() {

        try {
            File file = new File( this.localPathToFile + this.fileName );
            if (file.createNewFile()) {
                System.out.println("file created.");
            }
            else
                System.out.println("file already exists.");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try (FileReader fr = new FileReader(this.localPathToFile + this.fileName);
             BufferedReader br = new BufferedReader(fr);)
        {
            FileWriter fw = new FileWriter(this.localPathToFile + this.fileName, this.append);
            BufferedWriter bw = new BufferedWriter(fw);
            this.writer = new PrintWriter(bw);
            if(br.readLine() == null) {
                this.writer.println("episode, reward, epsilon");
            }

            this.writer.flush();

        } catch (IOException e) {
            System.out.println("Could not initialize CSV callback");
            e.printStackTrace();
        }
    }
}