package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.example.githubuser.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_AVATAR = "extra_avatar"

        private val TAB_CODES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailUserViewModel::class.java)
        val parameterUserName = intent.getStringExtra(EXTRA_USERNAME)

        if (parameterUserName != null) {
            viewModel.getUserDetail(parameterUserName)
            viewModel.getFollower(parameterUserName)
            viewModel.getFollowing(parameterUserName)
        }

        viewModel.detailUser.observe(this) { Name ->
            setDetailUser(Name)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        val sectionsPagerAdapter = SectionsPagerAdapter(this, parameterUserName)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            if (position == 1) {
                tab.text = resources.getString(TAB_CODES[position])
            } else {
                tab.text = resources.getString(TAB_CODES[position])
            }
        }.attach()
    }

    private fun setDetailUser(Name: DetailUserResponse?) {
        Glide.with(this@DetailUserActivity)
            .load(Name?.avatarUrl)
            .into(binding.avatarPhoto)
        binding.tvUsername.text = Name?.login
        binding.tvName.text = Name?.name
        binding.tvFollowerSize.text = Name?.followers.toString()
        binding.tvFollowingSize.text = Name?.following.toString()
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}