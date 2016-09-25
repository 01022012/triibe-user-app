package com.example.triibe.triibeuserapp.edit_survey;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.example.triibe.triibeuserapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @author michael.
 */

public class EditSurveyScreenTest {

    private static String TEST_SURVEY_ID = "Test";
    private static String TEST_SURVEY_DESCRIPTION = "Test";
    private static String TEST_SURVEY_VERSION = "Test";
    private static String TEST_SURVEY_POINTS = "Test";
    private static String TEST_SURVEY_TIME_TILL_EXPIRY = "Test";
    private static String TEST_QUESTION_ID = "q1";

    private IdlingResource mIdlingResource;

    @Rule
    public IntentsTestRule<EditSurveyActivity> mEditSurveyIntentsTestRule =
            new IntentsTestRule<>(EditSurveyActivity.class, true, false);

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mEditSurveyIntentsTestRule.getActivity().getCountingIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Before
    public void setupActivity() {
        Intent intent = new Intent();
        intent.putExtra(EditSurveyActivity.EXTRA_SURVEY_ID, TEST_SURVEY_ID);
        mEditSurveyIntentsTestRule.launchActivity(intent);
    }

    @Test
    public void enteringAnExistingQuestionIdLoadsSavedQuestionDetails() {
        onView(withId(R.id.survey_id)).perform(typeText(TEST_SURVEY_ID));
        onView(withId(R.id.survey_description)).check(matches(withText(TEST_SURVEY_DESCRIPTION)));
        onView(withId(R.id.survey_version)).check(matches(withText(TEST_SURVEY_VERSION)));
        onView(withId(R.id.survey_points)).check(matches(withText(TEST_SURVEY_POINTS)));
        onView(withId(R.id.survey_time_till_expiry)).check(matches(withText(TEST_SURVEY_TIME_TILL_EXPIRY)));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
