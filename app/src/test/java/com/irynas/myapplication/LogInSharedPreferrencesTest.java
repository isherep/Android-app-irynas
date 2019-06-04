package com.irynas.myapplication;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import com.irynas.myapplication.MainActivity;

import org.junit.Test;

/**
 * Verifyes text-entry validation logic correctly handles success & error conditions,
 */
public class LogInSharedPreferrencesTest {
    public class ValidEntryUnitTest {
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

}