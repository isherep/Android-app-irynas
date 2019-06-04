package com.irynas.myapplication;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

//package com.irynas.myapplication;

/**
 * Verify your application correctly reads from / writes to shared preferences
 */
@RunWith(AndroidJUnit4.class)
public class TestSharedPreferrences {
    private MainActivity mainActivity = new MainActivity();

    @Test
    public void shoudReturnTrueIfValidInput() {
        assertThat(mainActivity.validate("chowder", "bread"), is(true));
    }

    @Test
    public void shoudReturnFalseIfEmailEmpty() {
        assertThat(mainActivity.validate("", "bread"), is(false));
    }

    @Test
    public void shoudReturnFalseIfPasswordEmpty() {
        assertThat(mainActivity.validate("bread", ""), is(false));
    }

    @Test
    public void shoudReturnFalseIfInputNotValid() {
        assertThat(mainActivity.validate("12345", "1111111"), is(false));
    }
}
