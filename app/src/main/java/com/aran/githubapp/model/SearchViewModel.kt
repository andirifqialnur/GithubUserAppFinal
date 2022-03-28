package com.aran.githubapp.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aran.githubapp.api.Config
import com.aran.githubapp.data.dataset.UserResponse
import com.aran.githubapp.data.dataset.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private var listUsers = MutableLiveData<ArrayList<Users>>()
    fun setSearchUsers(username: String, context: Context) {
        Config.getService().getSearchUsers(username).enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    listUsers.postValue(response.body()?.items)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    Toast.makeText(context, "onFailure: ${response.message()}", Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    fun getSearchUsers(): LiveData<ArrayList<Users>> {
        return listUsers
    }

    companion object {
        const val TAG = "TAG"
    }
}