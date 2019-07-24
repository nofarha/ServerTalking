package com.example.servertalking;

import com.google.gson.annotations.SerializedName;

public class ImageUrl{

    @SerializedName("image_url")
    private String imageUrl;

    public ImageUrlHolder(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
