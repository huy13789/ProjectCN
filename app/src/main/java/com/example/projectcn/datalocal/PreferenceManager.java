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
        mySharedPreferences.putString(KEY_IDUSER, String.valueOf(id));
    }

    public String getId() {
        String idString = mySharedPreferences.getString(KEY_IDUSER);
        Long id = Long.parseLong(idString);
        try {
            return mySharedPreferences.getString(KEY_IDUSER);
        } catch (NumberFormatException e) {
            return null;
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
