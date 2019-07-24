package com.example.servertalking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ShowUserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_info);
        TextView nameTextView = findViewById(R.id.pretty_name_text_view);
        ImageView imageView = findViewById(R.id.imageView);
        Button editButton = findViewById(R.id.edit_button);

        String prettyName = getIntent().getStringExtra("pretty_name");
        Log.d("show user info", "show user pretty name: " + prettyName);
        String image_url = getIntent().getStringExtra("image_url");
        Log.d("show user info", "show user image url: " + image_url);

        nameTextView.setText("Welcome back, " + prettyName + "!");
        Glide.with(this).load(image_url).into(imageView);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditProfileActivity();
            }
        });
    }

    private void startEditProfileActivity() {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }
}
