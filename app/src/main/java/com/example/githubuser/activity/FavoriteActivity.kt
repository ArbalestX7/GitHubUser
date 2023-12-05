package com.example.githubuser.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapter.FavoriteUserAdapter
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.viewmodel.FavoriteViewModel
import com.example.githubuser.viewmodel.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity(), FavoriteUserAdapter.OnUserFavoriteClick {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteUserAdapter
    private val viewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModelFactory.getInstance(application)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getAllFavoriteUser().observe(this) { favUserList ->
            if (favUserList != null) {
                adapter.setListFavorite(favUserList)
                binding.tvNoDataUser.isVisible = favUserList.isEmpty()
            } else {
                binding.tvNoDataUser.isVisible = true
            }
        }
        adapter = FavoriteUserAdapter(this)

        binding.rvListUser.apply {
            val manager = LinearLayoutManager(this@FavoriteActivity)
            val decoration = DividerItemDecoration(this@FavoriteActivity, manager.orientation)
            adapter = this@FavoriteActivity.adapter
            layoutManager = manager
            addItemDecoration(decoration)
        }
        supportActionBar?.title = getString(R.string.favorite_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        menu.removeItem(R.id.search)
        menu.removeItem(R.id.favorite)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        } /*else if (item.itemId == R.id.setting) {
            SettingsActivity.start(this@FavoriteUserActivity)
        }*/
        onBackPressedDispatcher.addCallback(this@FavoriteActivity) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, FavoriteActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onUserFavoriteClick(username: String) {
        DetailUserActivity.start(this, username)
    }
}