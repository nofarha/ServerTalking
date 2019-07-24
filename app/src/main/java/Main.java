package com.example.servertalking;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main extends Application {

    ServerApi serverApi;

    private String userName;
    private String token;

    private SharedPreferences sharedPreferences;

    private static final String USER_NAME_KEY = "user name";
    private static final String TOKEN_KEY = "token";


    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hujipostpc2019.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serverApi = retrofit.create(ServerApi.class);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        userName = sharedPreferences.getString(USER_NAME_KEY, "");
        token = sharedPreferences.getString(TOKEN_KEY, "");

    }

    public String getUserName() {
        return userName;
    }

    void setUserName(String userName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME_KEY, userName);
        editor.apply();
        this.userName = userName;
    }

    public void setUserToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
