package com.example.servertalking.server
        ;


import retrofit2.Call;
import retrofit2.http.*;

public interface ServerApi {

    @GET("/")
    Call<ServerData> checkConnection();

    @GET("/users/{userName}/token")
    Call<ServerData> getUserToken(@Path("userName") String userName);

    @GET("user")
    Call<User> getUser(@Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST("/user/edit/")
    Call<User> updatePrettyName(@Header("Authorization") String token, @Body PrettyNameHolder prettyNameHolder);

    @Headers("Content-Type: application/json")
    @POST("/user/edit/")
    Call<User> updateImageUrl(@Header("Authorization") String token, @Body ImageUrlHolder imageUrlHolder);

}
