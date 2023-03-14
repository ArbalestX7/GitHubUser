package com.example.githubuser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getGitHubUser(
        @Query("q") query: String
    ): Call<GitHubUserResponse>

}