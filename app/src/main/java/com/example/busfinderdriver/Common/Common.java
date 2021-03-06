package com.example.busfinderdriver.Common;

import com.example.busfinderdriver.Remote.IGoogleAPI;
import com.example.busfinderdriver.Remote.RetrofitClient;

public class Common {
    public static final String baseURL = "https://maps.googleapis.com";
    public static IGoogleAPI getGoogleAPI(){
        return RetrofitClient.getClient(baseURL).create(IGoogleAPI.class);
    }
}
