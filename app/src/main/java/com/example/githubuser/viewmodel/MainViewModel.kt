package com.example.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.GitHubUserResponse
import com.example.githubuser.ItemsItem
import com.example.githubuser.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _username = MutableLiveData<List<ItemsItem>>()
    val username: LiveData<List<ItemsItem>> = _username

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
        private const val USERNAME = "Arif"
    }

    init {
        findGitHubUser(USERNAME)
    }

    fun findGitHubUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGitHubUser(query)
        client.enqueue(object : Callback<GitHubUserResponse> {
            override fun onResponse(
                call: Call<GitHubUserResponse>,
                response: Response<GitHubUserResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _username.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<GitHubUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}