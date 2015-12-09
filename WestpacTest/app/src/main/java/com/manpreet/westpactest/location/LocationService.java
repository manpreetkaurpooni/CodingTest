package com.manpreet.westpactest.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.manpreet.westpactest.R;
import com.manpreet.westpactest.service.ServiceException;
import com.manpreet.westpactest.util.AppUtils;
import com.manpreet.westpactest.util.LogTag;

/**
 * Created by manpreet on 9/12/15.
 */
public class LocationService implements LocationListener {

    private static LocationService instance = null;
    private LocationManager locationManager;
    private Location location;

    public static LocationService getInstance() throws ServiceException {
        if (instance == null) {
            instance = new LocationService();
        }
        return instance;
    }

    private LocationService() throws ServiceException {
        initLocationService();
    }

    private void initLocationService() throws ServiceException {
        try {
            this.locationManager = (LocationManager) AppUtils.getContext().getSystemService(Context.LOCATION_SERVICE);

            if (locationManager == null) {
                return;
            }

            boolean isProviderEnabled = locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isProviderEnabled) {
                location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            } else if (isNetworkEnabled) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (location == null) {
                throw new ServiceException(AppUtils.getContext().getResources().getString(R.string.network_providers_error));
            }

        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            Log.e(LogTag.EXCEPTION_TAG, e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public Location getLocation() {
        return this.location;
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

}