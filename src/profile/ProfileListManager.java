package profile;

import java.util.Iterator;
import java.util.LinkedList;

public class ProfileListManager {
    private LinkedList<Profile> profileList;

    public ProfileListManager(LinkedList<Profile> profileList) {
        this.profileList = profileList;
    }

    public void addProfile(Profile profile) {
        profileList.add(profile);
    }

    public void removeProfile(Profile profile) {
        profileList.remove(profile);
    }

    //find an account in the linked list based off a pin
    public Profile findProfile(String name) {
        Profile tempProfile;

        for (Iterator<Profile> i = profileList.iterator(); i.hasNext(); ) {
            tempProfile = i.next();

            if (tempProfile.verifyName(name)) {
                return tempProfile;
            }
        }

        //System.out.println("Bank Profile Not Found");
        return null;
    }
}
