package com.groundzero.github.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.groundzero.github.data.local.LocalRepository
import com.groundzero.github.data.remote.Github
import com.groundzero.github.data.remote.GithubDataSource
import com.groundzero.github.data.remote.GithubDataSourceFactory
import com.groundzero.github.data.remote.GithubRepository
import com.groundzero.github.data.response.StatusResponse
import com.groundzero.github.utils.LOCAL_REPOSITORY_KEY
import com.groundzero.github.utils.LOCAL_USERNAME_KEY

class ListViewModel(githubRepository: GithubRepository, private val localRepository: LocalRepository) : ViewModel() {

    val itemPagedList: LiveData<PagedList<Github>>
    // Used to manage status of the view
    val liveResponse: LiveData<StatusResponse>

    private var githubDataSourceFactory: GithubDataSourceFactory = GithubDataSourceFactory(githubRepository)
    private var liveDataSource: LiveData<GithubDataSource>

    init {
        liveDataSource = githubDataSourceFactory.getItemLiveDataSource()
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(GithubDataSource.REPO_JUMP_SIZE)
            .build()
        itemPagedList = LivePagedListBuilder(githubDataSourceFactory, config).build()

        liveResponse = Transformations.switchMap(
            githubDataSourceFactory.getItemLiveDataSource()
        ) { dataSource -> dataSource.getLiveResponse() }
    }
    // Chose SharedPreferences instead of passing data over viewModel or SQLite
    // to avoid unnecessary coupling on such small amount of data being passed
    fun cacheSelectedItemUsernameAndRepoName(username: String, repositoryName: String) {
        localRepository.setData(LOCAL_USERNAME_KEY, username).apply()
        localRepository.setData(LOCAL_REPOSITORY_KEY, repositoryName).apply()
    }
}