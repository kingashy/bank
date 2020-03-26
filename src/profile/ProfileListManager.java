package profile;

import java.util.HashMap;
import java.util.Objects;

public class ProfileListManager {
    private HashMap<Integer, Profile> profileList;

    public ProfileListManager(HashMap<Integer, Profile> profileList) {
        this.profileList = profileList;
    }

    //add a profile to the local list
    public void add(int key, Profile profile) {
        profileList.put(key, profile);
    }

    //remove a profile from the local list
    public void remove(int key) {
        profileList.remove(key);
    }

    //find an account in the linked list based off a name and ssn
    public Profile find(int key, String name) {
        Profile tempProfile = profileList.get(key);

        if (tempProfile != null && tempProfile.verifyName(name)) return tempProfile;

        System.out.println("Bank Profile Not Found");
        return null;
    }

}
