package com.irynas.myapplication;
//package com.example.android.testing.espresso.BasicSample;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewAction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static android.support.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoUITest {

    private String stringToBetyped;
    private String passwordToBetyped;

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        stringToBetyped = "espresso";
        passwordToBetyped = "123";
    }

    /*
    I see you're using AndroidX. Make sure you are using the correct import.
    I ran in to this exact issue today and realized I was still using the old
    import android.support.test.runner.AndroidJUnit4;
    when I should have been using import androidx.test.runner.AndroidJUnit4;
     */
    @Test
    public void changeUsernamePasswordAndLoginButton() {
        // Testing email field.
        onView(withId(R.id.email))
                .perform(typeText(stringToBetyped), closeSoftKeyboard());

        //testing button
        onView(withId(R.id.login)).perform((ViewAction) click());

        // Testing password field.
        onView(withId(R.id.password))
                .perform((ViewAction) typeText(passwordToBetyped))
                .check(matches(withText(passwordToBetyped)));
    }



}
