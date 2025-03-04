package com.capstoneproject.purrsonalcatapp.data.remote.retrofit

import com.capstoneproject.purrsonalcatapp.data.remote.response.LoginResponse
import com.capstoneproject.purrsonalcatapp.data.remote.response.RegisterResponse
import com.capstoneproject.purrsonalcatapp.data.remote.response.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("auth/register")
    suspend fun registerUser(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("auth/me")
    suspend fun getUserData(): UserResponse
}