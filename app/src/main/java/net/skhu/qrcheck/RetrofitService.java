package net.skhu.qrcheck;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    public Retrofit retrofit;
    public RetrofitAPI retrofitAPI;
    //    private final String BASE_URL = "http://10.0.2.2:8080";
    private final String BASE_URL = "http://192.168.0.163:8080";

    public void init() {
        // GSON 컨버터를 사용하는 REST 어댑터 생성
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
