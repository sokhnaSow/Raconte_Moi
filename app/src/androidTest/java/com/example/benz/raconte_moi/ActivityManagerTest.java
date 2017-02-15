package com.example.benz.raconte_moi;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by BENZ on 15/02/2017.
 */

public class ActivityManagerTest {
    @Rule
    public ActivityTestRule<ActivityManager> m = new ActivityTestRule<ActivityManager>(ActivityManager.class);

    @Test
    public void testWritngBtn() throws  Exception {
        onView(withId(R.id.writingBtn)).perform(click());
        onView(withId(R.id.drawingBtn)).check(matches(isDisplayed()));

    }
    @Test
    public void testReadingBtn() throws  Exception {
        onView(withId(R.id.readingBtn)).perform(click());

    }
}
