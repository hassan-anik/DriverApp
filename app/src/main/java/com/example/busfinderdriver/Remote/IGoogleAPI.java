package com.example.busfinderdriver.Remote;

//import android.telecom.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import retrofit2.Call;

import java.lang.annotation.Target;

public interface IGoogleAPI {
    @GET
    Call<String> getPath(@Url String url);
}
