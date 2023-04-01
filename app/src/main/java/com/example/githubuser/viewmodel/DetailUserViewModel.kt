package com.example.githubuser.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.ItemsItem
import com.example.githubuser.data.FavUserRepository
import com.example.githubuser.database.FavUserEntity
import com.example.githubuser.response.DetailUserResponse
import com.example.githubuser.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : ViewModel() {

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoadingUser = MutableLiveData<Boolean>()
    val isLoadingUser: LiveData<Boolean> = _isLoadingUser

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    private val mFavUserRepository: FavUserRepository =
        FavUserRepository(application)

    init {
        getUserDetail()
    }

    fun getUserDetail(query: String = "") {
        _isLoadingUser.value = true
        val client = ApiConfig.getApiService().getDetailUser(query)
        client.enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoadingUser.value = false
                if (response.isSuccessful){
                    _detailUser.value = response.body()
                }else{
                    Log.e(TAG, "onFailure: ${response.code()}")
                }
            }

            override fun onFailure(
                call: Call<DetailUserResponse>, t: Throwable
            ) {
                _isLoadingUser.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollower(query: String = "") {
        val client = ApiConfig.getApiService().getFollowers(query)
        client.enqueue(object: Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>,
            ) {
                if (response.isSuccessful){
                    _followers.value = response.body()
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}" )
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
    fun getFollowing(query: String = "") {
        val client = ApiConfig.getApiService().getFollowing(query)
        client.enqueue(object: Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>,
            ) {
                if (response.isSuccessful){
                    _following.value = response.body()
                }else{
                    Log.e(TAG, "onFailure: ${response.code()}" )
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun insert (favUserEntity: FavUserEntity) {
        mFavUserRepository.insert(favUserEntity)
    }

    fun delete(id: Int) {
        mFavUserRepository.delete(id)
    }

    fun getFavorite():LiveData<List<FavUserEntity>> =
        mFavUserRepository.getAllFavorites()

    companion object{
        const val TAG = "DetailUserViewModel"
    }
}