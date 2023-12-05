package com.example.githubuser.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.DetailUserViewModel
import com.example.githubuser.R
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.database.FavUserEntity
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.example.githubuser.response.DetailUserResponse
import com.example.githubuser.viewmodel.FavoriteViewModel
import com.example.githubuser.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailUserViewModel>()
    private lateinit var favoriteViewModel: FavoriteViewModel

    private lateinit var favUser: FavUserEntity
    private var mCurrentToast: Toast? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favoriteViewModel = obtainViewModel(this@DetailUserActivity)

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

        if (userLogin != null) {
            showLoadingUser(true)
            detailViewModel.getUserDetail(userLogin)
            showLoadingUser(false)
        }
        detailViewModel.detailUser.observe(this, Observer { detailUser ->
            setDetailUser(detailUser)

        })

        detailViewModel.isLoadingUser.observe(this, {
            showLoadingUser(it)
        })

        binding.fabFav.apply {
            setOnClickListener {
                if (tag == "favorite") {
                    favoriteViewModel.delete(favUser)
                    showToast(context.getString(R.string.remove_favorite_user))
                } else {
                    favoriteViewModel.insert(favUser)
                    showToast(context.getString(R.string.add_favorite_user))
                }
            }
        }
    }


    private fun setFavoriteUser(value: Boolean) {
        binding.fabFav.apply {
            if (value) {
                tag = "favorite"
                setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailUserActivity,
                        R.drawable.ic_fav_white
                    )
                )
            } else {
                tag = ""
                setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailUserActivity,
                        R.drawable.ic_favorite_border
                    )
                )
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

        favUser = FavUserEntity(Name!!.login, Name.avatarUrl)
        favoriteViewModel.isFavorite(favUser.login).observe(this) {
            setFavoriteUser(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
            }
            R.id.favorite -> {
                FavoriteActivity.start(this@DetailUserActivity)
            }
            R.id.setting -> {
                SettingsActivity.start(this@DetailUserActivity)
            }
        }
        onBackPressedDispatcher.addCallback(this@DetailUserActivity) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showLoadingUser(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        if (mCurrentToast != null) {
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

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    companion object {
        fun start(context: Context, username: String) {
            val starter = Intent(context, DetailUserActivity::class.java)
                .putExtra(EXTRA_USER, username)
            context.startActivity(starter)
        }

        const val EXTRA_USER = "key_user"

        @StringRes
        private val TAB_CODES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}