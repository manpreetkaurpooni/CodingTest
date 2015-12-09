package com.manpreet.westpactest.test;

import com.manpreet.westpactest.MainActivity;
import com.manpreet.westpactest.R;
import com.robotium.solo.*;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import junit.framework.Assert;

/**
 * Created by manpreet on 9/12/15.
 */

public class TestWeather extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    private MainActivity mainActivity;

    public TestWeather() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        mainActivity = getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testRun() {
        solo.waitForActivity(com.manpreet.westpactest.MainActivity.class, 2000);
        solo.waitForView(R.id.current_weather, 1, 3500);
        TextView weatherTextView = (TextView) solo.getView(R.id.current_weather);
        String weather = weatherTextView.getText().toString();
        Assert.assertTrue(AppUtils.weatherApiValues.contains(weather));
    }
}
