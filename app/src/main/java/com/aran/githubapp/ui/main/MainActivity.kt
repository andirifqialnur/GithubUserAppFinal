package com.aran.githubapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aran.githubapp.R
import com.aran.githubapp.adapter.UsersAdapter
import com.aran.githubapp.data.dataset.Users
import com.aran.githubapp.databinding.ActivityMainBinding
import com.aran.githubapp.model.AllUsersViewModel
import com.aran.githubapp.model.SearchViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UsersAdapter
    private lateinit var allUserViewModel: AllUsersViewModel
    private lateinit var searchUserModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerViewList()
        showLoading(true)
        searchViewUser()
        showAllUsers()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.favorite_user -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.settings -> {
                val intentSetting = Intent(this, SettingsActivity::class.java)
                startActivity(intentSetting)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun recyclerViewList() {
        userAdapter = UsersAdapter()

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = userAdapter
        binding.rvUser.setHasFixedSize(true)

        userAdapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickedCallback {
            override fun onItemClicked(data: Users) {
                userDataSelected(data)
            }
        })
    }

    private fun userDataSelected(user: Users) {
        val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USER, user.login)
        this@MainActivity.startActivity(intent)
    }

    private fun showAllUsers() {
        allUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(AllUsersViewModel::class.java)
        allUserViewModel.setAllUsers(this)
        allUserViewModel.getAllUsers().observe(this) { listUsers ->
            if (listUsers != null) {
                userAdapter.setData(listUsers)
                showLoading(false)
            }
        }
    }

    private fun showSearchDataUsers(username: String) {
        searchUserModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(SearchViewModel::class.java)
        searchUserModel.setSearchUsers(username, this)
        showLoading(true)
        searchUserModel.getSearchUsers().observe(this) { listSearchUsers ->
            if (listSearchUsers != null) {
                userAdapter.setData(listSearchUsers)
                showLoading(false)
            }
        }
    }

    private fun searchViewUser() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    return true
                } else {
                    showSearchDataUsers(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    showAllUsers()
                } else {
                    showLoading(false)
                    showSearchDataUsers(newText)
                    showLoading(true)
                }
                return true
            }
        })
    }
}