package com.manpreet.westpactest.listener;

import com.manpreet.westpactest.model.Weather;
import com.manpreet.westpactest.service.ServiceException;

import java.io.Serializable;

/**
 * Created by manpreet on 9/12/15.
 */
public interface WeatherAsyncTaskListener {

    public void taskCompleted(Weather weather);

    public void taskCompletedWithException(ServiceException exception);
}
