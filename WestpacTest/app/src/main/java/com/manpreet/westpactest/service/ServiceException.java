package com.manpreet.westpactest.service;

import android.util.Log;

import com.manpreet.westpactest.util.LogTag;

/**
 * Created by manpreet on 9/12/15.
 */
public class ServiceException extends Exception {

    private String errorMessage = "";
    private String serverMessage = "";
    private int serverMessageStatusCode = 0;

    public ServiceException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        logErrorMessage(errorMessage);
    }

    public ServiceException(String errorMessage, String serverMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.serverMessage = serverMessage;
        logErrorMessage(errorMessage);
    }

    public ServiceException(String errorMessage, String serverMessage, int serverStatusCode) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.serverMessage = serverMessage;
        this.serverMessageStatusCode = serverStatusCode;
        logErrorMessage(errorMessage);

    }

    public String getErrorMessage() {
        return errorMessage;
    }


    public String getServerMessage() {
        return serverMessage;
    }

    public int getStatusCode() {
        return serverMessageStatusCode;
    }

    protected void logErrorMessage(String message) {
        Log.e(LogTag.EXCEPTION_TAG, message);
    }

    public String getDetailedErrorMessage() {
        String detailedMessage = errorMessage + " "  +serverMessage;
        if(serverMessageStatusCode > 0) {
            detailedMessage += "\n" + "Status code: " + serverMessageStatusCode;
        }
        return detailedMessage;
    }

}

