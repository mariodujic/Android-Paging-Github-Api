package com.groundzero.github.ui.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.groundzero.github.data.local.LocalRepository
import com.groundzero.github.data.remote.Contributor
import com.groundzero.github.data.remote.Github
import com.groundzero.github.data.remote.GithubRepository
import com.groundzero.github.data.response.StatusResponse
import com.groundzero.github.utils.LOCAL_REPOSITORY_KEY
import com.groundzero.github.utils.LOCAL_USERNAME_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoViewModel(private val githubRepository: GithubRepository, private val localRepository: LocalRepository) :
    ViewModel() {

    private val repositoryLiveData = MutableLiveData<Github>()
    private val contributorsLiveData = MutableLiveData<List<Contributor>>()
    private val responseLiveData = MutableLiveData(StatusResponse.loading())

    private val username = localRepository.getData(LOCAL_USERNAME_KEY)
    private val repositoryName = localRepository.getData(LOCAL_REPOSITORY_KEY)

    init {
       fetchData()
    }

    private fun fetchData() {
        if (username != null && repositoryName != null) {
            fetchRepositoryLiveData()
            fetchContributorsLiveData()
        } else {
            responseLiveData.value = StatusResponse.error(Throwable())
        }
    }

    private fun fetchRepositoryLiveData() {
        githubRepository.getSingleRepository(username!!, repositoryName!!)
            .enqueue(object : Callback<Github> {
                override fun onResponse(call: Call<Github>, response: Response<Github>) {
                    if (response.isSuccessful) {
                        repositoryLiveData.value = response.body()
                        responseLiveData.value = StatusResponse.success()
                    }
                }

                override fun onFailure(call: Call<Github>, throwable: Throwable) {
                    responseLiveData.value = StatusResponse.error(throwable)
                }
            })
        responseLiveData.value = StatusResponse.success()

    }

    private fun fetchContributorsLiveData() {
        githubRepository.getRepositoryContributors(username!!, repositoryName!!)
            .enqueue(object : Callback<List<Contributor>> {

                override fun onResponse(call: Call<List<Contributor>>, response: Response<List<Contributor>>) {
                    if (response.isSuccessful) {
                        contributorsLiveData.value = response.body()
                        responseLiveData.value = StatusResponse.success()
                    }
                }

                override fun onFailure(call: Call<List<Contributor>>, throwable: Throwable) {
                    responseLiveData.value = StatusResponse.error(throwable)
                }
            })
    }

    fun getRepositoryLiveData(): LiveData<Github> = repositoryLiveData
    fun getContributorsLiveData(): LiveData<List<Contributor>> = contributorsLiveData
}