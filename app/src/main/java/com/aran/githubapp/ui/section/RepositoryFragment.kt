package com.aran.githubapp.ui.section

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aran.githubapp.R
import com.aran.githubapp.adapter.RepositoriesAdapter
import com.aran.githubapp.model.RepositoryViewModel

class RepositoryFragment : Fragment() {

    private lateinit var adapter: RepositoriesAdapter
    private lateinit var getRepoModel: RepositoryViewModel

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val USERNAME = "section_username"

        @JvmStatic
        fun newInstance(index: Int, username: String) = RepositoryFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_SECTION_NUMBER, index)
                putString(USERNAME, username)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repo, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RepositoriesAdapter()

        val tvRepos: RecyclerView = view.findViewById(R.id.rv_repo)
        tvRepos.layoutManager = LinearLayoutManager(this.context)
        tvRepos.adapter = adapter

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 2)
        val username = arguments?.getString(USERNAME)
        val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)

        if (index == 2) {
            showRepositories(username.toString(), progressBar)
        }
    }

    private fun showRepositories(username: String, progressBar: ProgressBar) {

        getRepoModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(RepositoryViewModel::class.java)
        getRepoModel.setListRepository(username, requireContext())
        getRepoModel.getListRepository().observe(viewLifecycleOwner) { listRepo ->
            if (listRepo != null) {
                progressBar.visibility = View.GONE
                adapter.setData(listRepo)
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }
}