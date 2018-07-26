package com.parassidhu.cdlumaths.activities;

import android.app.Activity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public abstract class RequestInterface extends Activity{

    public interface RetrofitInterface {
        @GET
        @Streaming
        Call<ResponseBody> downloadFile(@Url String url);
    }
}