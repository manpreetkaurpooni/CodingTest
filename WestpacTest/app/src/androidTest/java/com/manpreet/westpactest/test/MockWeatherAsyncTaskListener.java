package com.manpreet.westpactest.test;

import com.manpreet.westpactest.listener.WeatherAsyncTaskListener;
import com.manpreet.westpactest.model.Weather;
import com.manpreet.westpactest.service.ServiceException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by manpreet on 9/12/15.
 */
public class MockWeatherAsyncTaskListener implements WeatherAsyncTaskListener {

    @Override
    public void taskCompleted(Weather weather) {
        assertTrue(weather != null);
        assertTrue(AppUtils.weatherApiValues.contains(weather.currentWeather));
    }

    @Override
    public void taskCompletedWithException(ServiceException exception) {
        assertFalse(exception.getDetailedErrorMessage(), exception != null);
    }
}
