package com.irynas.myapplication;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Context;
import android.content.SharedPreferences;

import com.irynas.myapplication.SharedPreferencesHelper;
/**
 * Verify your application correctly reads from / writes to shared preferences
 */
@RunWith(MockitoJUnitRunner.class)

public class LogInSharedPreferrencesTest {

    private MainActivity mainActivity = new MainActivity();


    final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);


    @Mock
    SharedPreferences mockPreferences;
    @Mock
    SharedPreferences.Editor mockEditor;



    private SharedPreferencesHelper mockHelper;
    private String text_entry = "text";


    @Before
    public void initMocks(){
        mockHelper = createMockSharedPreference();
    }

    /**
     * Tests if the input was successfully stored and retrieves data in shared preferences
     */
    @Test
    public void validateInputStoringInPreferences(){

        boolean success = mockHelper.saveEntry(text_entry);

        assertThat("SharedPreferenceEntry.save... returns true", success, is(true));
        //assertEquals(text_entry, mockHelper.getEntry());
    }


    @Test
    public void validateInputRetrievesFromPreferences(){
        assertEquals(text_entry, mockHelper.getEntry());

    }


    //create method
    private SharedPreferencesHelper createMockSharedPreference(){

        when(mockPreferences.getString(eq("text_entry"), anyString()))
            .thenReturn(text_entry);

        //mocking successful commit
        when(mockEditor.commit()).thenReturn(true);

        //Return an editor per request
        when(mockPreferences.edit()).thenReturn(mockEditor);
         //return new helper object based on mock preference
        return new SharedPreferencesHelper(mockPreferences);

    }




}