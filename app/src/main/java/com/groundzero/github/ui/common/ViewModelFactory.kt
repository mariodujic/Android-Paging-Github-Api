package com.groundzero.github.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.groundzero.github.data.local.LocalRepository
import com.groundzero.github.data.remote.GithubRepository
import com.groundzero.github.ui.list.ListViewModel
import com.groundzero.github.ui.single.RepoViewModel

class ViewModelFactory constructor(
    private val remoteRepository: GithubRepository,
    private val localRepository: LocalRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            ListViewModel(remoteRepository, localRepository) as T
        } else if (modelClass.isAssignableFrom(RepoViewModel::class.java)) {
            RepoViewModel(remoteRepository, localRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}