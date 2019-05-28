package com.mvvmsample.network;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mvvmsample.BuildConfig;
import com.mvvmsample.application.MyApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClient {
    private static final String TAG = RetroFitClient.class.getCanonicalName();
    private Retrofit retrofit;
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_PRAGMA = "Pragma";
    private static RetroFitClient retroFitClient;

    /**
     * @return retroFitClient
     */
    public synchronized static RetroFitClient getRetroFitClient() {
        if (retroFitClient == null)
            retroFitClient = new RetroFitClient();
        return retroFitClient;
    }

    /**
     * Private constructor
     */
    private RetroFitClient() {
    }


    public Retrofit getRetroFit() {
        if (retrofit == null) {
            initRetroFit();
        }
        return retrofit;
    }


    private void initRetroFit() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(getGSON()))
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHTTpClient())
                .build();
    }

    private OkHttpClient getOkHTTpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(getLoggingInterceptor())
                .readTimeout(30,TimeUnit.SECONDS)
                .connectTimeout(30,TimeUnit.SECONDS)
                .addInterceptor(provideOfflineCacheInterceptor())
                .addNetworkInterceptor(provideCacheInterceptor())
                .cache(getCache())
                .build();
    }

    private HttpLoggingInterceptor getLoggingInterceptor() {
        return new HttpLoggingInterceptor(message -> {
            Log.e(TAG, message + "");
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private Cache getCache() {
        if (MyApplication.getApplication() != null) {
            int cacheSize = 10 * 1024 * 1024; //10 MB Cache
            return new Cache(MyApplication.getApplication().getCacheDir(), cacheSize);
        }
        return null;
    }

    private Gson getGSON() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    private Interceptor provideCacheInterceptor() {
        return chain -> {
            Response response = chain.proceed(chain.request());
            CacheControl cacheControl;
            if (CheckConnection.isConnected(MyApplication.getApplication())) {
                cacheControl = new CacheControl.Builder()
                        .maxAge(0, TimeUnit.SECONDS)
                        .build();
            } else {
                cacheControl = new CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build();
            }
            return response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build();

        };
    }

    private Interceptor provideOfflineCacheInterceptor() {
        return chain -> {
            Request request = chain.request();
            if (CheckConnection.isConnected(MyApplication.getApplication())) {
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build();
                request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build();
            }
            return chain.proceed(request);
        };
    }

}

