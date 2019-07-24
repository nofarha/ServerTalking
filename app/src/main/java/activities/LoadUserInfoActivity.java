package com.example.servertalking;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadUserInfoActivity extends AppCompatActivity {

    private AlertDialog noConnectionDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_user_info);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Unable to contact server.\nPlease check your internet connection and try again later.");
        alertDialogBuilder.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        noConnectionDialog = alertDialogBuilder.create();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getUserInfoFromServer();
            }
        }, 2000);

    }

    private void getUserInfoFromServer() {
        final MyApp app = (MyApp) getApplicationContext();
        String token = app.getToken();
        Call<User> call = app.serverApi.getUser("token " + token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Log.d("server contact", "response not successful with code: " + response.code());
                    noConnectionDialog.show();
                }

                else {
                    Log.d("load user info", "load user json: " + response.body().getData());
                    startShowUserActivity(response.body());
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("server contact", "response FAILLL 121: " + t.getMessage());
                noConnectionDialog.show();
            }
        });
    }

    private void startShowUserActivity(User user) {
        finish();
        Intent intent = new Intent(this, ShowUserInfoActivity.class);
        String prettyName = user.getData().get("pretty_name");
        if (prettyName == null || prettyName.equals(""))
            prettyName = user.getData().get("username");

        String image_url = "https://hujipostpc2019.pythonanywhere.com" + user.getData().get("image_url");
        intent.putExtra("pretty_name", prettyName);
        intent.putExtra("image_url",image_url);
        startActivity(intent);
    }
}
