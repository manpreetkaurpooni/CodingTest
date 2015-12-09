package com.manpreet.westpactest.service;

import android.content.Context;
import android.util.Log;

import com.manpreet.westpactest.R;
import com.manpreet.westpactest.util.AppUtils;
import com.manpreet.westpactest.util.LogTag;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ServiceAdapter {

    public static final int HTTP_CONNECTION_TIMEOUT = 30000;

    public static String executeGetRequest(String urlString) throws ServiceException {

        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(HTTP_CONNECTION_TIMEOUT);
            urlConnection.setConnectTimeout(HTTP_CONNECTION_TIMEOUT);
            urlConnection.setUseCaches(true);

            InputStream inputStream = urlConnection.getInputStream();
            int statusCode = urlConnection.getResponseCode();
            String responseMessage = urlConnection.getResponseMessage();

            if (statusCode == 200) {
                JSONObject resJson = new JSONObject(getResponseText(inputStream));
                return resJson.toString();
            } else {
                throw new ServiceException(ctx.getResources().getString(R.string.weather_api_error), responseMessage, statusCode);
            }

        } catch (ConnectException e) {
            Log.e(LogTag.EXCEPTION_TAG, e.toString());
            throw new ServiceException(
                    ctx.getResources().getString(R.string.network_error), e.toString());
        } catch (ServiceException e) {
            Log.e(LogTag.EXCEPTION_TAG, e.toString());
            throw e;
        } catch (Exception e) {
            Log.e(LogTag.EXCEPTION_TAG, e.toString());
            throw new ServiceException(ctx.getResources().getString(R.string.weather_api_error), e.toString());
        }
    }

    private static String getResponseText(InputStream inStream) {
        return new Scanner(inStream).useDelimiter("\\A").next();
    }

    private static Context ctx = AppUtils.getContext();

    public void setContext(Context context) {
        this.ctx = context;
    }
}

