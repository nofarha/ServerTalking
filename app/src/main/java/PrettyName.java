package com.example.servertalking;

import com.google.gson.annotations.SerializedName;

public class PrettyName {
    @SerializedName("pretty_name")
    private String prettyName;

    public PrettyNameHolder(String prettyName) {
        this.prettyName = prettyName;
    }

    public String getPrettyName() {
        return prettyName;
    }
}
