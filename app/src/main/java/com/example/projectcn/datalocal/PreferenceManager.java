package com.example.projectcn.datalocal;

import android.content.Context;

public class PreferenceManager {
    private static final String PREF_NAME = "appPreferences";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_IDUSER = "idUser";
    private static final String KEY_PASSWORD = "userpassword";
    private static final String KEY_REMEMBER = "remember";

    private MySharedPreferences mySharedPreferences;

    public PreferenceManager(Context context) {
        mySharedPreferences = new MySharedPreferences(context);
    }

    public void saveId(Long id) {
        mySharedPreferences.putString(KEY_IDUSER, String.valueOf(id)); // Convert Long to String and save
    }

    public Long getId() {
        String idString = mySharedPreferences.getString(KEY_IDUSER);
        try {
            return Long.parseLong(idString); // Convert String back to Long
        } catch (NumberFormatException e) {
            return null; // Return null if the string cannot be parsed into a Long
        }
    }

    public void saveUsername(String username) {
        mySharedPreferences.putString(KEY_USERNAME, username);
    }

    public String getUsername() {
        return mySharedPreferences.getString(KEY_USERNAME);
    }

    public void saveUserPW(String userpassword) {
        mySharedPreferences.putString(KEY_PASSWORD, userpassword);
    }

    public String getUserPW() {
        return mySharedPreferences.getString(KEY_PASSWORD);
    }

    public void saveRememberState(boolean isRemembered) {
        mySharedPreferences.putBoolean(KEY_REMEMBER, isRemembered);
    }

    public boolean getRememberState() {
        return mySharedPreferences.getBoolean(KEY_REMEMBER, false);
    }
}
