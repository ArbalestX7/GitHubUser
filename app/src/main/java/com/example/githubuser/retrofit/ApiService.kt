package com.example.githubuser.retrofit

import com.example.githubuser.response.DetailUserResponse
import com.example.githubuser.GitHubUserResponse
import com.example.githubuser.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getGitHubUser(
        @Query("q") query: String
    ): Call<GitHubUserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") login: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>


}