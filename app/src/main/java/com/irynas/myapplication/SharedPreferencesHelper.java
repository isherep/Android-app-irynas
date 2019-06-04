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
     * @return - boolean if the entry writing to the Editor succeded
     */
    public boolean saveEntry(String entry){
        //initialize the Editor
        SharedPreferences.Editor mockEditor = mockSharedPreferences.edit();
        //now when there is an editor we can save to it.
        mockEditor.putString(KEY, entry);

        return mockEditor.commit();
    }

    /**
     * Retrieves entered String value at the given key
     * Default retrieved if empty value set is empty string
     * @return entered string
     */
    public String getEntry(){

        return mockSharedPreferences.getString(KEY, "");


    };


    //java.lang.RuntimeException: Method isEmpty in android.text.TextUtils not mocked
}

