package com.model.Profile;

import java.util.List;

public class ProfileHistory {
    private List<Profile> savedProfiles;

    public ProfileHistory(List<Profile> savedProfiles){
        this.savedProfiles = savedProfiles;
    }

    public void addProfile(PreModifiedProfile newSave) {
        savedProfiles.add(newSave);
    }

    public List<Profile> getSavedProfiles() {
        return savedProfiles;
    }

    // Helper Method to call RestoreProfile()
    public Profile getLatestProfile() {
        int index = savedProfiles.size() -1;
        return savedProfiles.get(index);
    }
}
