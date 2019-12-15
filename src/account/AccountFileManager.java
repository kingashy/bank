package account;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import file.FileManager;

public class AccountFileManager {
    private static String delimeter = ",";

    private FileManager fileManager;
    private String fileName;

    public AccountFileManager(FileManager fileManager, String fileName) {
        this.fileManager = fileManager;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    //add an account to the accounts.txt file
    public void add(Account account) {
        try {
            //open the buffer in append mode
            BufferedWriter buffW = new BufferedWriter(new FileWriter(fileName, true));

            buffW.write((account.toString()).trim());
            buffW.newLine();

            buffW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //remove an account to the accounts.txt file
    public void remove(Account account) {
        fileManager.removeLineFromFile(fileName, account.toString());
    }

    public String[] parseLine(BufferedReader buffR) {
        return fileManager.parseLine(buffR);
    }
}
