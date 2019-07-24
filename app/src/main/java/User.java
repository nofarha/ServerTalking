package com.example.servertalking;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class User {

    @SerializedName("data")
    private HashMap<String, String> data;

    public HashMap<String, String> getData() {
        return data;
    }

}
