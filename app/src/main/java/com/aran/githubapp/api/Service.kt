package com.aran.githubapp.api

import com.aran.githubapp.data.dataset.DetailUsers
import com.aran.githubapp.data.dataset.Repositories
import com.aran.githubapp.data.dataset.UserResponse
import com.aran.githubapp.data.dataset.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {

    @GET("users")
    @Headers("Authorization: token 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")
    fun getUsers(): Call<ArrayList<Users>>

    @GET("search/users")
    fun getSearchUsers(
        @Query("q") q: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUsers(
        @Path("username") username: String
    ): Call<Users>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<Users>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<Users>>

    @GET("users/{username}/repos")
    fun getUserRepository(
        @Path("username") username: String
    ): Call<ArrayList<Repositories>>

}