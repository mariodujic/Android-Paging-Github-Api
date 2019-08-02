package com.groundzero.github.ui.single


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.groundzero.github.R
import com.groundzero.github.core.activity.RemoteBaseFragment
import kotlinx.android.synthetic.main.fragment_repo.*


class RepoFragment : RemoteBaseFragment() {

    private lateinit var repoViewModel: RepoViewModel
    private lateinit var repoAdapter: RepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repoViewModel = ViewModelProviders.of(this, this.viewModelFactory).get(RepoViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarVisibility(repo_progress_bar as ProgressBar, true)
        repoViewModel.getRepositoryLiveData().observe(viewLifecycleOwner, Observer { repository ->
            run {
                size.text = resources.getString(R.string.repo_size, repository.size)
                stargazers_count.text = resources.getString(R.string.repo_stargazers, repository.stargazersCount)
                forks_count.text = resources.getString(R.string.repo_forks, repository.forksCount)
                progressBarVisibility(repo_progress_bar as ProgressBar, false)
            }
        })

        repoViewModel.getContributorsLiveData().observe(viewLifecycleOwner, Observer { contributors ->
            run {
                repoAdapter = RepoAdapter(context!!, contributors)
                mutableRecyclerView(contributors_recycler_view).adapter = repoAdapter
                progressBarVisibility(repo_progress_bar as ProgressBar, false)
            }
        })
    }
}