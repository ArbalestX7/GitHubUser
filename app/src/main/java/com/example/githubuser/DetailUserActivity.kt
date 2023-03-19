package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    companion object {
        const val EXTRA_USER = "key_user"

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
        val userLogin = intent.getStringExtra(EXTRA_USER)
        binding.tvName.text = userLogin


        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = userLogin.toString()
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_CODES[position])
        }.attach()

        if (userLogin != null){
            showLoading(true)
            viewModel.getUserDetail(userLogin)
            showLoading(false)
        }
        viewModel.detailUser.observe(this, {detailUser ->
            setDetailUser(detailUser)
        })
        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

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