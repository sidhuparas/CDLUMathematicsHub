package com.parassidhu.cdlumaths.activities;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public abstract class RequestInterface extends AppCompatActivity {

    public interface RetrofitInterface {
        @GET
        @Streaming
        Call<ResponseBody> downloadFile(@Url String url);
    }
}