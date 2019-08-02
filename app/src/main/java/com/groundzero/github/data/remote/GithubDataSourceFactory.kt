package com.groundzero.github.data.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource


class GithubDataSourceFactory(private val repository: GithubRepository) : DataSource.Factory<Int, Github>() {

    private val itemLiveDataSource = MutableLiveData<GithubDataSource>()
    private lateinit var githubDataSource: GithubDataSource

    override fun create(): DataSource<Int, Github> {
        githubDataSource = GithubDataSource(repository)
        itemLiveDataSource.postValue(githubDataSource)
        return githubDataSource
    }

    fun getItemLiveDataSource() = itemLiveDataSource
}