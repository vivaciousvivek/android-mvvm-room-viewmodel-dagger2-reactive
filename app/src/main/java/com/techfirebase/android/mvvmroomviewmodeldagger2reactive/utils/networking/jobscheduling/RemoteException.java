package com.techfirebase.android.mvvmroomviewmodeldagger2reactive.utils.networking.jobscheduling;

import retrofit2.Response;

public class RemoteException extends Exception {

    private final Response response;

    public RemoteException(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
