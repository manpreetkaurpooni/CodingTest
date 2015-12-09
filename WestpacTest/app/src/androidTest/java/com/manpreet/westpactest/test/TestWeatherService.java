package com.manpreet.westpactest.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;

import com.manpreet.westpactest.MainActivity;
import com.manpreet.westpactest.service.ServiceAdapter;
import com.manpreet.westpactest.service.WeatherServiceAdapter;
import com.manpreet.westpactest.task.GetWeatherStatusAsyncTask;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * Created by manpreet on 9/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestWeatherService extends ActivityInstrumentationTestCase2<MainActivity> {

    MockWeatherAsyncTaskListener mockWeatherAsyncTaskListener;

    CountDownLatch signal;

    @Spy
    WeatherServiceAdapter weatherServiceAdapter;

    @InjectMocks
    GetWeatherStatusAsyncTask getWeatherStatusAsyncTask;

    ServiceAdapter serviceAdapter;

    public TestWeatherService() {
        super(MainActivity.class);
    }


    @Override
    public void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache",
                getInstrumentation().getTargetContext().getCacheDir().getPath());
        signal = new CountDownLatch(1);
        weatherServiceAdapter = Mockito.mock(WeatherServiceAdapter.class, RETURNS_DEEP_STUBS);
        getWeatherStatusAsyncTask = new GetWeatherStatusAsyncTask();
        serviceAdapter = new ServiceAdapter();
        serviceAdapter.setContext(getActivity());
        mockWeatherAsyncTaskListener = new MockWeatherAsyncTaskListener();
        String validUrl = "https://api.forecast.io/forecast/fa9f3bd4630a056f8d68bce7f01e214b/1,1";
        when(weatherServiceAdapter.getWeatherStatus()).thenReturn(serviceAdapter.executeGetRequest(validUrl));
        getWeatherStatusAsyncTask.setListener(mockWeatherAsyncTaskListener);
        getWeatherStatusAsyncTask.setServiceAdapter(weatherServiceAdapter);

    }
    
    @UiThreadTest
    public void testValidLatLng() throws InterruptedException
    {
        getWeatherStatusAsyncTask.execute();
        signal.await(20, TimeUnit.SECONDS);
    }

}
