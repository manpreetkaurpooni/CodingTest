package com.manpreet.westpactest.model;

import java.io.Serializable;

/**
 * Created by manpreet on 9/12/15.
 */
public class Weather implements Serializable {

    public final String currentWeather;

    public Weather(String currentWeather) {
        this.currentWeather = currentWeather;
    }
}
