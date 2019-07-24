package com.example.servertalking;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LaunchActivity extends AppCompatActivity {
    private AlertDialog noConnectionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

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
                connectToServer();
            }
        }, 2000);
    }

    private void connectToServer() {
        MyApp app = (MyApp) getApplicationContext();
        Call<ServerData> call = app.serverApi.checkConnection();
        call.enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(Call<ServerData> call, Response<ServerData> response) {
                if (!response.isSuccessful()) {
                    Log.d("server contact", "response not successful with code: " + response.code());
                    noConnectionDialog.show();
                }

                else {
                    ServerData r = response.body();
                    Log.d("server contact", "response successful with data: " + r.getData());
                    startSuccessfulConnectionActivity();
                }

            }

            @Override
            public void onFailure(Call<ServerData> call, Throwable t) {
                Log.d("server contact", "response FAILLL: " + t.getMessage());
                noConnectionDialog.show();
            }
        });
    }

    private void startSuccessfulConnectionActivity() {
        finish();
        Intent intent = new Intent(this, ConnectionSuccessfulActivity.class);
        startActivity(intent);
    }

}
