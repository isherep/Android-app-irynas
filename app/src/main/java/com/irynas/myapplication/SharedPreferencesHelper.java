package com.irynas.myapplication;

import android.content.SharedPreferences;

import java.security.Key;

/**
 * Mocks the methods of shared preferences to be used during unit testing using Mockito mock object
 */
public class SharedPreferencesHelper {

    //key for the entry
    private final String KEY = "text_entry";
    //reference to shared preferences
    private final SharedPreferences mockSharedPreferences;

    public SharedPreferencesHelper(SharedPreferences sharedPreferences){
        mockSharedPreferences = sharedPreferences;
    }

    /**
     * Saves the String entry to the mock shared preferences object
     */
    public boolean saveEntry(String entry){

        return true;
    }

    /**
     * Retrieves entered String value at the given key
     * @return entered string
     */
    public boolean getEntry(){
        return mockSharedPreferences.getString(KEY, "")
    };
}

