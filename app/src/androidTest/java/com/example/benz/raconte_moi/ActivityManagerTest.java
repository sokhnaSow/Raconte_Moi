package com.example.benz.raconte_moi;

import android.provider.Settings;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.NavigationViewActions.navigateTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by BENZ on 15/02/2017.
 */
@RunWith(AndroidJUnit4.class)
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
        onView(withId(R.id.activity_reading_manager)).check(matches(isDisplayed()));
    }
}
