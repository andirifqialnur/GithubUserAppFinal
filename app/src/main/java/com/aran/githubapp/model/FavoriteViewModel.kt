package com.aran.githubapp.model

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aran.githubapp.data.local.*

class FavoriteViewModel : ViewModel() {

    private var _userList = MutableLiveData<ArrayList<DataRoom>>()
    private var listUser: LiveData<ArrayList<DataRoom>> = _userList

    fun getDataFavorite(context: Context): LiveData<ArrayList<DataRoom>>{
        val database = DatabaseRoom.getDatabase(context.applicationContext).dataDao()
        _userList.value = database.getAllData() as ArrayList<DataRoom>
        listUser = _userList

        return listUser
    }
}