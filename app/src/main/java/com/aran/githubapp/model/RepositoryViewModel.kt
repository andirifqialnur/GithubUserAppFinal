package com.aran.githubapp.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aran.githubapp.api.Config
import com.aran.githubapp.data.dataset.Repositories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel : ViewModel() {

    val listRepository = MutableLiveData<ArrayList<Repositories>>()

    fun setListRepository(username: String, context: Context) {
        Config.getService().getUserRepository(username).enqueue(object : Callback<ArrayList<Repositories>> {
                override fun onResponse(
                    call: Call<ArrayList<Repositories>>,
                    response: Response<ArrayList<Repositories>>
                ) {
                    if (response.isSuccessful) {
                        listRepository.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ArrayList<Repositories>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                    Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_LONG)
                        .show()
                }

            })
    }

    fun getListRepository(): LiveData<ArrayList<Repositories>> = listRepository

    companion object {
        const val TAG = "TAG"
    }
}