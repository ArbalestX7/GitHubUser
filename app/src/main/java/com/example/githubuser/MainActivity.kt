package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter

    companion object {
        private const val TAG = "MainActivity"
        private const val  GITHUBUSER_ID = "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"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvGituser.layoutManager = layoutManager
        binding.apply {
            rvGituser.layoutManager = layoutManager
            rvGituser.setHasFixedSize(true)
            rvGituser.adapter = adapter
        }
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvGituser.addItemDecoration(itemDecoration)

        findGitHubUser()

    }
    private fun findGitHubUser(){
        showLoading(true)
        val client = ApiConfig.getApiService().getGitHubUser(GITHUBUSER_ID)
        client.enqueue(object : Callback<GitHubUserResponse>{
            override fun onResponse(
                call: Call<GitHubUserResponse>,
                response: Response<GitHubUserResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        setGitHubUserData(responseBody.items)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GitHubUserResponse>, t: Throwable) {
                showLoading (false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setGitHubUserData (gitHubUserResponseItem: List<ItemsItem>){
        val listGitHubUserResponseItem = ArrayList<String>()
        for (data in gitHubUserResponseItem) {
            listGitHubUserResponseItem.add(
                """
                    ${data.login}
                """.trimIndent()
            )
            listGitHubUserResponseItem.add(
                """
                    ${data.type}
                """.trimIndent()
            )
            listGitHubUserResponseItem.add(
                """
                    ${data.avatarUrl}
                """.trimIndent()
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}