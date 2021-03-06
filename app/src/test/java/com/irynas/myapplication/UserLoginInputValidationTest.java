package com.irynas.myapplication;

import android.content.Context;
//import android.support.test.InstrumentationRegistry;
//import android.support.test.runner.JUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

//package com.irynas.myapplication;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import com.irynas.myapplication.MainActivity;

import org.junit.Test;
import org.junit.runners.JUnit4;

/**
 * Verifyes text-entry validation logic correctly handles success & error conditions,
 */
@RunWith(JUnit4.class)
public class UserLoginInputValidationTest {
    private MainActivity mainActivity = new MainActivity();

    @Test
    public void shoudReturnTrueIfValidInput() {
        assertThat(mainActivity.validate("chowder", "bread", "crab"), is(true));
    }

    @Test
    public void shoudReturnFalseIfEmailEmpty() {
        assertThat(mainActivity.validate("bread", "", "joeshmoe"), is(false));
    }

    @Test
    public void shoudReturnFalseIfPasswordEmpty() {
        assertThat(mainActivity.validate("bread", "", "salad"), is(false));
    }

    @Test
    public void shoudReturnFalseIfNameEmpty() {
        assertThat(mainActivity.validate("", "joeshmoe@gmail.com", "salad"), is(false));
    }


}
