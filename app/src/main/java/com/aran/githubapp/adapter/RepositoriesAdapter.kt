package com.aran.githubapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aran.githubapp.data.dataset.Repositories
import com.aran.githubapp.databinding.ItemUsersRepoBinding

class RepositoriesAdapter: RecyclerView.Adapter<RepositoriesAdapter.RepositoryViewHolder>() {

    private val listRepo = ArrayList<Repositories>()

    private var onItemClickedCallback: OnItemClickedCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(itemData: ArrayList<Repositories>) {
        listRepo.clear()
        listRepo.addAll(itemData)
        notifyDataSetChanged()
    }

    inner class RepositoryViewHolder(private val binding: ItemUsersRepoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repos: Repositories) {
            binding.root.setOnClickListener {
                onItemClickedCallback?.onItemClicked(repos)
            }

            binding.apply {
                tvItemProjectName.text = repos.name
                tvItemDesc.text = repos.description
                lang.text = repos.language
                size.text = repos.size.toString()
                watchers.text = repos.watchers.toString()
                forks.text = repos.forks.toString()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = ItemUsersRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder((view))
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(listRepo[position])
    }

    override fun getItemCount(): Int = listRepo.size

    interface OnItemClickedCallback {
        fun onItemClicked(data: Repositories)
    }
}