package com.eldsoke.islam.dmstask;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by islam on 1/3/2018.
 */

public class WebService {
    private static WebService instance;
    private API api;
    private Context context;
    private Cache cache = null;


    public WebService() {
        cache = new Cache(MyCustomApplication.getPath(), 10 * 1024 * 1024);
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (!MyCustomApplication.isOnline()) {
                            CacheControl cacheControl = new CacheControl.Builder()
                                    .maxStale(7, TimeUnit.DAYS)
                                    .build();
                            request = request.newBuilder().cacheControl(cacheControl).build();
                        }
                        return chain.proceed(request);
                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder().client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();
        api = retrofit.create(API.class);
    }

    public static WebService getInstance() {

        if (instance == null) {
            instance = new WebService();
        }
        return instance;
    }


    public API getApi() {
        return api;
    }

    public void deletCach() throws IOException {

        cache.delete();
    }

}
