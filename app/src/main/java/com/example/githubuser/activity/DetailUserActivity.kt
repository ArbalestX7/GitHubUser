package com.example.githubuser.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.example.githubuser.response.DetailUserResponse
import com.example.githubuser.viewmodel.DetailUserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private var mCurrentToast: Toast? = null


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
        val viewPager: ViewPager2 = binding.viewPager2
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_CODES[position])
        }.attach()

        if (userLogin != null){
            showLoadingUser(true)
            viewModel.getUserDetail(userLogin)
            showLoadingUser(false)
        }
        viewModel.detailUser.observe(this, {detailUser ->
            setDetailUser(detailUser)
        })
        viewModel.isLoadingUser.observe(this, {
            showLoadingUser(it)
        })

        binding.fabFav.apply {
            setOnClickListener{
                if ( tag == "favorite") setFavoriteUser(false)
                else setFavoriteUser(true)
            }
        }
    }

    private fun setFavoriteUser(value: Boolean) {
        binding.fabFav.apply {
            if (value) {
                tag == "favorite"
                setImageDrawable(ContextCompat.getDrawable(this@DetailUserActivity,R.drawable.ic_fav_white))
                showToast("Added to Favorite")
            } else {
                tag = ""
                setImageDrawable(ContextCompat.getDrawable(this@DetailUserActivity,R.drawable.ic_favorite_border))
                showToast("Removed from Favorite")
            }
        }
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


    private fun showLoadingUser(isLoading: Boolean) {
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        if(mCurrentToast != null) {
            mCurrentToast?.cancel()
        }
        mCurrentToast = Toast.makeText(this, message, duration)
        mCurrentToast?.show()
    }

    @Suppress("DEPRECATION")
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }
    companion object {
        const val EXTRA_USER = "key_user"
        @StringRes
        private val TAB_CODES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}