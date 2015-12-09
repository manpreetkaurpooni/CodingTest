package com.manpreet.westpactest.service;

import android.location.Location;

import com.manpreet.westpactest.location.LocationService;

/**
 * Created by manpreet on 9/12/15.
 */
public class WeatherServiceAdapter extends ServiceAdapter {

    private final static String MY_API_KEY = "fa9f3bd4630a056f8d68bce7f01e214b";
    private final static String WEATHER_HOST = "https://api.forecast.io/forecast/";

    public String getWeatherStatus() throws ServiceException {
        String url = getWeatherHostUrl();
        return executeGetRequest(url);
    }

    private String getWeatherHostUrl() throws ServiceException {
        Location location = getLocation();
        return WEATHER_HOST +  MY_API_KEY + "/" + location.getLatitude() + "," + location.getLongitude();
    }

    private Location getLocation() throws ServiceException {
        LocationService locationService = LocationService.getInstance();
        Location location = locationService.getLocation();
        return location;
    }

}
