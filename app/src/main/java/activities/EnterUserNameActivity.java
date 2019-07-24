package com.example.servertalking;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterUserNameActivity extends AppCompatActivity {

    private AlertDialog noConnectionDialog;


    private EditText userNameEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_user_name);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Unable to contact server.\nPlease check your internet connection and try again later.");
        alertDialogBuilder.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        noConnectionDialog = alertDialogBuilder.create();

        userNameEditText = findViewById(R.id.editText);
        saveButton = findViewById(R.id.button);

        saveButton.setEnabled(false);

        userNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 1 && isNameLegal()) {
                    saveButton.setEnabled(true);
                } else {
                    saveButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEditText.getText().toString();
                setTokenAndUserName(userName);
            }
        });
    }

    private boolean isNameLegal() {
        String userName = userNameEditText.getText().toString();
        for (char c : userName.toCharArray()) {
            if (!Character.isLetterOrDigit(c))
                return false;
        }
        return true;
    }

    private void startLoadUserInfoActivity() {
        finish();
        Intent intent = new Intent(this, LoadUserInfoActivity.class);
        startActivity(intent);
    }

    private void setTokenAndUserName(final String userName) {
        final MyApp app = (MyApp) getApplicationContext();
        Call<ServerData> call = app.serverApi.getUserToken(userName);
        call.enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(Call<ServerData> call, Response<ServerData> response) {
                if (!response.isSuccessful()) {
                    Log.d("server contact", "response not successful with code: " + response.code());
                    noConnectionDialog.show();
                }

                else {
                    ServerData serverData = response.body();
                    app.setUserToken(serverData.getData());
                    app.setUserName(userName);
                    startLoadUserInfoActivity();
                }

            }

            @Override
            public void onFailure(Call<ServerData> call, Throwable t) {
                Log.d("server contact", "response FAILLL: " + t.getMessage());
                noConnectionDialog.show();
            }
        });
    }


}
