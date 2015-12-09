package com.manpreet.westpactest.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manpreet on 9/12/15.
 */
public class AppUtils {

    public static List<String> weatherApiValues = new ArrayList<String>();

    static{
        weatherApiValues.add("clear-day");
        weatherApiValues.add("clear-night");
        weatherApiValues.add("rain");
        weatherApiValues.add("snow");
        weatherApiValues.add("sleet");
        weatherApiValues.add("wind");
        weatherApiValues.add("fog");
        weatherApiValues.add("cloudy");
        weatherApiValues.add("partly-cloudy-day");
        weatherApiValues.add("partly-cloudy-night");
        weatherApiValues.add("hail");
        weatherApiValues.add("thunderstorm");
        weatherApiValues.add("tornado");
    }

}
