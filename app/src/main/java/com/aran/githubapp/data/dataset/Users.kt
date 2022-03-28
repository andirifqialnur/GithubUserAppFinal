package com.aran.githubapp.data.dataset

import com.google.gson.annotations.SerializedName

data class Users(

    val avatar_url: String,
    val name: String,
    val login: String,
    val bio: String,
    val followers: Int,
    val following: Int,
    val public_repos: Int,
    val followers_url: String,
    val following_url: String,
    val repos_url: String,
    val id: Int,

    @field:SerializedName("html_url")
    var user_url: String? = null
)
