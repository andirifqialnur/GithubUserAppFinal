package com.aran.githubapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aran.githubapp.data.local.DataRoom
import com.aran.githubapp.databinding.ItemUsersBinding
import com.aran.githubapp.ui.main.DetailUserActivity
import com.bumptech.glide.Glide

class FavoritesAdapter(private val list: ArrayList<DataRoom>, private val activity: Activity) :
    RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataRoom) {
            binding.tvItemUsername.text = data.username.toString()
            binding.tvItemId.text = data.id.toString()
            Glide.with(itemView)
                .load(data.avatar)
                .into(binding.imgItemPhoto)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USER, data.username.toString())
                activity.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
