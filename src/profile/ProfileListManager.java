package profile;

import java.util.HashMap;
import java.util.Objects;

public class ProfileListManager {
    private HashMap<Integer, Profile> profileList;

    public ProfileListManager(HashMap<Integer, Profile> profileList) {
        this.profileList = profileList;
    }

    public void addProfile(int hash, Profile profile) {
        profileList.put(hash, profile);
    }

    public void removeProfile(int hash) {
        profileList.remove(hash);
    }

    //find an account in the linked list based off a name and ssn
    public Profile findProfile(int hash, String name) {
        Profile tempProfile = profileList.get(hash);

        if (tempProfile != null && tempProfile.verifyName(name)) return tempProfile;

        System.out.println("Bank Profile Not Found");
        return null;
    }

}
