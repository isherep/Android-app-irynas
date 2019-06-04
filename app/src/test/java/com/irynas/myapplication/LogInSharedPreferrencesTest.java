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

    @Test
    public void validateInputStoringInPreferences(){

    }


    public void validateInputRetrievesFromPreferences(){

    }





}