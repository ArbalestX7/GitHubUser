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