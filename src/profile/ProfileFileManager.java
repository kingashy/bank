package profile;

import file.FileManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ProfileFileManager {
    private FileManager fileManager;
    private String fileName;

    public ProfileFileManager(FileManager fileManager, String fileName) {
        this.fileManager = fileManager;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void addProfile(Profile profile) {
        try {
            //open the buffer in append mode
            BufferedWriter buffW = new BufferedWriter(new FileWriter(fileName, true));

            buffW.write((profile.toString()).trim());
            buffW.newLine();

            buffW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeProfile(Profile profile) {
        fileManager.removeLineFromFile(fileName, profile.toString());
    }

    public String[] parseLine(BufferedReader buffR) {
        return fileManager.parseLine(buffR);
    }
}
