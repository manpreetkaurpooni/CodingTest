package com.manpreet.westpactest;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manpreet.westpactest.databinding.FragmentWeatherBinding;
import com.manpreet.westpactest.listener.MainActivityListener;
import com.manpreet.westpactest.model.Weather;

/**
 * Created by manpreet on 9/12/15.
 */
public class WeatherFragment extends Fragment {

    private MainActivityListener mainActivityListener;
    private Weather weather;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mainActivityListener = (MainActivityListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        Bundle bundle = getArguments();

        if (savedInstanceState != null) {
            weather = (Weather) savedInstanceState.getSerializable("weather");
        } else if (bundle != null) {
            weather = (Weather) bundle.getSerializable("weather");
        }

        FragmentWeatherBinding binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_weather);
        binding.setWeather(weather);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("weather", weather);
    }
}
