package com.groundzero.github.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.groundzero.github.data.response.StatusResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubDataSource(private val repository: GithubRepository) : PageKeyedDataSource<Int, Github>() {

    val liveResponse = MutableLiveData<StatusResponse>()

    companion object {
        // Data starts from initial github repository item
        private const val CURRENT_REPOSITORY = 0
        // Data pagination
        public var REPO_JUMP_SIZE = 25
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Github>) {

        liveResponse.postValue(StatusResponse.loading())

        repository.getRepositories(CURRENT_REPOSITORY).enqueue(object : Callback<List<Github>> {
            override fun onResponse(call: Call<List<Github>>, response: Response<List<Github>>) {
                if (response.isSuccessful) {
                    liveResponse.postValue(StatusResponse.success())
                    callback.onResult(
                        response.body()!!.subList(0, REPO_JUMP_SIZE),
                        null,
                        CURRENT_REPOSITORY + REPO_JUMP_SIZE
                    )
                }
            }

            override fun onFailure(call: Call<List<Github>>, t: Throwable) {
                liveResponse.postValue(StatusResponse.error(t))
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Github>) {

        liveResponse.postValue(StatusResponse.loading())

        repository.getRepositories(params.key).enqueue(object : Callback<List<Github>> {
            override fun onResponse(call: Call<List<Github>>, response: Response<List<Github>>) {
                if (response.isSuccessful) {
                    liveResponse.postValue(StatusResponse.success())
                    callback.onResult(response.body()!!.subList(0, REPO_JUMP_SIZE), params.key + REPO_JUMP_SIZE)
                }
            }

            override fun onFailure(call: Call<List<Github>>, t: Throwable) {
                liveResponse.postValue(StatusResponse.error(t))
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Github>) {
    }

    fun getLiveResponse(): LiveData<StatusResponse> = liveResponse
}