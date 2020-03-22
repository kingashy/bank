package profile;

import file.FileManager;

import java.io.*;
import java.util.LinkedList;

public class ProfileFileManager extends FileManager {
    private ProfileListManager profileListManager;

    public ProfileFileManager(ProfileListManager profileListManager, String fileName) {
        super(fileName);
        this.profileListManager = profileListManager;

        setup();
    }

    private void setup() {
        try {
            String tempParse[];
            BufferedReader buffR = new BufferedReader(new FileReader(getFileName()));

            do {
                tempParse = parseLine(buffR);
                if (tempParse == null) break;
                addProfileFromFile(tempParse);
            } while (tempParse != null);

            buffR.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addProfileFromFile(String[] splitLine) {
        Profile profile;
        LinkedList<Long> accountNumberList = new LinkedList<Long>();

        for (int i = 2; i <= splitLine.length - 1; i++) {
            accountNumberList.add(Long.parseLong(splitLine[i].trim()));
        }
        //System.out.println(accountNumberList);

        profile = new Profile(splitLine[0].trim(), Integer.parseInt(splitLine[1].trim()), accountNumberList);
        profileListManager.add(Integer.parseInt(splitLine[1].trim()), profile);
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
        removeLineFromFile(profile.toString());
    }
}
