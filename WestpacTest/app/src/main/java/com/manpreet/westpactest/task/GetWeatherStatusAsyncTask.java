package com.manpreet.westpactest.task;

import android.os.AsyncTask;
import android.util.Log;

import com.manpreet.westpactest.listener.WeatherAsyncTaskListener;
import com.manpreet.westpactest.model.Weather;
import com.manpreet.westpactest.service.WeatherServiceAdapter;
import com.manpreet.westpactest.service.ServiceException;
import com.manpreet.westpactest.util.LogTag;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by manpreet on 9/12/15.
 */
public class GetWeatherStatusAsyncTask extends AsyncTask<Void, String, Void> {
    private ServiceException exception = null;
    private WeatherAsyncTaskListener asyncTaskListener;
    private WeatherServiceAdapter weatherServiceAdapter;
    private Weather weather;
    private final static String jsonCurrently = "currently";
    private final static String jsonIcon = "icon";

    public GetWeatherStatusAsyncTask() {
    }

    public void setListener(WeatherAsyncTaskListener asyncTaskListener){
        this.asyncTaskListener = asyncTaskListener;
    }

    public void setServiceAdapter(WeatherServiceAdapter weatherServiceAdapter){
        this.weatherServiceAdapter = weatherServiceAdapter;
    }

    protected void onPreExecute() {
        ;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            String response = weatherServiceAdapter.getWeatherStatus();
            JSONObject jsonObject = new JSONObject(response);
            String currentWeather = jsonObject.getJSONObject(jsonCurrently).getString(jsonIcon);
            weather = new Weather(currentWeather);
        } catch (JSONException e) {
            exception = new ServiceException(e.getMessage());
        } catch (ServiceException e) {
            exception = e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (exception != null) {
            Log.e(LogTag.EXCEPTION_TAG, exception.toString());
            asyncTaskListener.taskCompletedWithException(exception);
        } else {
            asyncTaskListener.taskCompleted(weather);
        }
    }
}