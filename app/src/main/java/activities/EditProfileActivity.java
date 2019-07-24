package com.example.servertalking;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    EditText nameEditText;

    private AlertDialog noConnectionDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Unable to contact server.\nPlease check your internet connection and try again later.");
        alertDialogBuilder.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        noConnectionDialog = alertDialogBuilder.create();

        nameEditText = findViewById(R.id.new_name_edit_text);

        ImageView octopusView = findViewById(R.id.octopus_image_view);
        ImageView crabView = findViewById(R.id.crab_image_view);
        ImageView alienView = findViewById(R.id.alien_image_view);
        ImageView frogView = findViewById(R.id.frog_image_view);
        ImageView robotView = findViewById(R.id.robot_image_view);
        ImageView unicornView = findViewById(R.id.unicorn_image_view);

        String base_url = "https://hujipostpc2019.pythonanywhere.com";

        Glide.with(this).load(base_url + "/images/octopus.png").into(octopusView);
        Glide.with(this).load(base_url + "/images/crab.png").into(crabView);
        Glide.with(this).load(base_url + "/images/alien.png").into(alienView);
        Glide.with(this).load(base_url + "/images/frog.png").into(frogView);
        Glide.with(this).load(base_url + "/images/robot.png").into(robotView);
        Glide.with(this).load(base_url + "/images/unicorn.png").into(unicornView);

        octopusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageUrl("/images/octopus.png");
            }
        });

        crabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageUrl("/images/crab.png");
            }
        });

        alienView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageUrl("/images/alien.png");
            }
        });

        frogView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageUrl("/images/frog.png");
            }
        });

        robotView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageUrl("/images/robot.png");
            }
        });

        unicornView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageUrl("/images/unicorn.png");
            }
        });

        Button saveNameButton = findViewById(R.id.save_new_name_button);
        saveNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewName();
            }
        });
    }

    private void saveImageUrl(String imageUrl) {
        final MyApp app = (MyApp) getApplicationContext();
        String token = "token " + app.getToken();
        Call<User> call = app.serverApi.updateImageUrl(token, new ImageUrlHolder(imageUrl));
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

    private void saveNewName() {
        String prettyName = nameEditText.getText().toString();
        final MyApp app = (MyApp) getApplicationContext();
        String token = "token " + app.getToken();
        Call<User> call = app.serverApi.updatePrettyName(token, new PrettyNameHolder(prettyName));
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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        String prettyName = user.getData().get("pretty_name");
        if (prettyName == null || prettyName.equals(""))
            prettyName = user.getData().get("username");

        String image_url = "https://hujipostpc2019.pythonanywhere.com" + user.getData().get("image_url");
        intent.putExtra("pretty_name", prettyName);
        intent.putExtra("image_url",image_url);
        startActivity(intent);
    }
}
