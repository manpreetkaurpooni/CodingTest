package com.manpreet.westpactest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.manpreet.westpactest.listener.MainActivityListener;
import com.manpreet.westpactest.listener.WeatherAsyncTaskListener;
import com.manpreet.westpactest.model.Weather;
import com.manpreet.westpactest.service.ServiceException;
import com.manpreet.westpactest.service.WeatherServiceAdapter;
import com.manpreet.westpactest.task.GetWeatherStatusAsyncTask;
import com.manpreet.westpactest.util.AppUtils;
import com.manpreet.westpactest.util.PermissionUtil;

public class MainActivity extends AppCompatActivity implements MainActivityListener, WeatherAsyncTaskListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private static String[] LOCATION_PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int REQUEST_LOCATION = 0;
    private TextView errorTextView;
    private boolean locationPermissionError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppUtils.setContext(this);
        errorTextView = (TextView) findViewById(R.id.error_message);
        errorTextView.setVisibility(View.GONE);

        if (savedInstanceState != null) {
            WeatherFragment weatherFragment = (WeatherFragment) getSupportFragmentManager().getFragment(savedInstanceState, "weatherFragment");
            if (weatherFragment != null) {
                startFragment(weatherFragment);
            } else {
                checkPermissionsAndGetWeatherStatus();
            }
        } else {
            checkPermissionsAndGetWeatherStatus();
        }

    }

    private void checkPermissionsAndGetWeatherStatus() {
        if (locationPermissionsRequired()) {
            requestLocationPermission();
        } else {
            getWeatherStatus();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (locationPermissionError) {
            checkPermissionsAndGetWeatherStatus();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Fragment weatherFragment = getSupportFragmentManager().findFragmentByTag("weatherFragment");

        if (weatherFragment != null) {
            getSupportFragmentManager().putFragment(outState, "weatherFragment", weatherFragment);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void startFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, "weatherFragment");
        fragmentTransaction.commit();
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, LOCATION_PERMISSIONS, REQUEST_LOCATION);
    }

    private boolean locationPermissionsRequired() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == REQUEST_LOCATION) {
            if (PermissionUtil.permissionGranted(grantResults)) {
                locationPermissionError = false;
                getWeatherStatus();
            } else {
                errorTextView.setVisibility(View.VISIBLE);
                locationPermissionError = true;
                errorTextView.setText(R.string.location_permission_error);
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void taskCompleted(Weather weather) {
        WeatherFragment weatherFragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable("weather", weather);
        weatherFragment.setArguments(args);
        startFragment(weatherFragment);
    }

    public void getWeatherStatus() {
        GetWeatherStatusAsyncTask getWeatherStatusAsyncTask = new GetWeatherStatusAsyncTask();
        getWeatherStatusAsyncTask.setListener(this);
        getWeatherStatusAsyncTask.setServiceAdapter(new WeatherServiceAdapter());
        getWeatherStatusAsyncTask.execute();
    }

    @Override
    public void taskCompletedWithException(ServiceException exception) {
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(exception.getDetailedErrorMessage());
    }


}
