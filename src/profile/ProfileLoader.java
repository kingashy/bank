package profile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProfileLoader
{
    private ProfileManager profileManager;
    private ProfileListManager profileListManager;
    private ProfileFileManager profileFileManager;

    public ProfileLoader(ProfileManager profileManager, ProfileListManager profileListManager, ProfileFileManager profileFileManager)
    {
        this.profileManager = profileManager;
        this.profileListManager = profileListManager;
        this.profileFileManager = profileFileManager;

        setup();
    }

    private void setup()
    {
        try
        {
            String tempParse[];
            Profile tempProfile;

            BufferedReader buffR = new BufferedReader(new FileReader(profileFileManager.getFileName()));

            do
            {
                tempParse = profileFileManager.parseLine(buffR);
                if (tempParse == null) break;
                tempProfile = profileManager.createProfileFromFile(tempParse);
                //tempProfile.showInfo();
                profileListManager.addProfile(tempProfile);
            }  while (tempParse != null);

            buffR.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
