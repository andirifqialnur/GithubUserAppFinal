package com.aran.githubapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.aran.githubapp.R
import com.aran.githubapp.adapter.SectionPagerAdapter
import com.aran.githubapp.data.dataset.Users
import com.aran.githubapp.data.local.DataDao
import com.aran.githubapp.data.local.DataRoom
import com.aran.githubapp.data.local.DatabaseRoom
import com.aran.githubapp.databinding.ActivityDetailUserBinding
import com.aran.githubapp.model.DetailUserViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var getUserModel: DetailUserViewModel
    private lateinit var database: DataDao

    private var url = ""
    private var userName = ""
    private var avatar = ""

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra(EXTRA_USER).toString()
        val actionbar = supportActionBar
        actionbar?.title = userName
        actionbar?.setDisplayHomeAsUpEnabled(true)

        database = DatabaseRoom.getDatabase(applicationContext).dataDao()
        val exist = database.dataExist(userName)
        setIcon(exist)

        showLoading(true)
        showDataUser(userName)

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager = binding.viewpager
        viewPager.adapter = sectionsPagerAdapter
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
        sectionsPagerAdapter.username = userName

        binding.favorite.setOnClickListener(this)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                detImg.visibility = View.GONE
                detUsername.visibility = View.GONE
                detName.visibility = View.GONE
                detRepo.visibility = View.GONE
                detBio.visibility = View.GONE
                detFollowers.visibility = View.GONE
                detFollowing.visibility = View.GONE
                viewpager.visibility = View.GONE
                favorite.visibility = View.GONE
                tabs.visibility = View.GONE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.GONE
                detImg.visibility = View.VISIBLE
                detUsername.visibility = View.VISIBLE
                detName.visibility = View.VISIBLE
                detRepo.visibility = View.VISIBLE
                detBio.visibility = View.VISIBLE
                detFollowers.visibility = View.VISIBLE
                detFollowing.visibility = View.VISIBLE
                viewpager.visibility = View.VISIBLE
                favorite.visibility = View.VISIBLE
                tabs.visibility = View.VISIBLE
            }
        }
    }

    private fun showDataUser(username: String) {
        getUserModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailUserViewModel::class.java)
        getUserModel.setUserDetail(username, this)
        getUserModel.getUserDetail().observe(this) { userData ->
            if (userData != null) {
                Log.d("tag", userData.toString())
                view(userData)
                showLoading(false)
            }
        }
    }

    private fun view(user: Users) {
        url = user.user_url.toString()
        avatar = user.avatar_url.toString()
        Glide.with(this)
            .load(user.avatar_url)
            .apply(RequestOptions().override(150, 150))
            .into(binding.detImg)
        binding.detName.text = user.name
        binding.detUsername.text = user.login
        binding.detBio.text = user.bio
        binding.detFollowing.text = user.following.toString()
        binding.detFollowers.text = user.followers.toString()
        binding.detRepo.text = user.public_repos.toString()
    }

    override fun onClick(v: View) {
        when (v) {
            binding.favorite -> {
                addFavorite(v)
            }
        }
    }

    private fun addFavorite(v: View) {
        val exist = database.dataExist(userName)
        setIcon(exist, v)
    }

    private fun setIcon(exist: Boolean) {
        if (exist) {
            binding.favorite.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_favorite
                )
            )
        } else {
            binding.favorite.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_favorite_white
                )
            )
        }
    }

    private fun setIcon(exist: Boolean, v: View) {
        if (!exist) {
            setIcon(true)
            Snackbar.make(v, "$userName ${getString(R.string.succes_add)}", Snackbar.LENGTH_SHORT)
                .show()
            val newData = DataRoom(
                username = userName,
                avatar = avatar,
            )
            database.insert(newData)
        } else {
            setIcon(false)
            Snackbar.make(
                v,
                "$userName ${getString(R.string.success_delete)}",
                Snackbar.LENGTH_SHORT
            ).show()
            database.delete(userName)
        }
    }

    private fun shareUser() {
        val mimeType = "text/plain"
        ShareCompat
            .IntentBuilder(this@DetailUserActivity)
            .setType(mimeType)
            .setChooserTitle("Link Url Github")
            .setText(url)
            .startChooser()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.share -> {
                shareUser()
            }
        }
    }

    companion object {
        const val EXTRA_USER = "extra_data"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2,
            R.string.tab_3
        )
    }
}