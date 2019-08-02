package com.groundzero.github.core.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.groundzero.github.data.local.LocalRepository
import com.groundzero.github.data.remote.GithubDatabase
import com.groundzero.github.data.remote.GithubRepository
import com.groundzero.github.ui.common.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_repo.*

open class RemoteBaseFragment : Fragment() {
    private val githubDatabase = GithubDatabase()
    // Remote repository
    private val githubRepository = GithubRepository(githubDatabase)
    // Local repository used to cache item name and item owner name
    // before remotely fetching more details about the item
    protected lateinit var localRepository: LocalRepository
    protected lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localRepository = LocalRepository(context!!)
        viewModelFactory = ViewModelFactory(githubRepository, localRepository)
    }

    protected fun mutableRecyclerView(recyclerView: RecyclerView): RecyclerView {
        recyclerView.setHasFixedSize(true)
        val linearLayout = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayout
        return recyclerView
    }

    protected fun progressBarVisibility(progressBar: ProgressBar, isVisible: Boolean) {
        progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}