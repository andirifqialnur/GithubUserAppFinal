package com.aran.githubapp.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aran.githubapp.api.Config
import com.aran.githubapp.data.dataset.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    private val followersModel = MutableLiveData<ArrayList<Users>>()

    fun setData(username: String, context: Context) {
        Config.getService().getFollowers(username).enqueue(object : Callback<ArrayList<Users>> {
            override fun onResponse(
                call: Call<ArrayList<Users>>,
                response: Response<ArrayList<Users>>
            ) {
                if (response.isSuccessful) {
                    followersModel.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<Users>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                Toast.makeText(context,"onFailure: ${t.message.toString()}", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    fun getData(): LiveData<ArrayList<Users>> = followersModel

    companion object {
        const val TAG = "TAG"
    }
}