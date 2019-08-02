package com.groundzero.github.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("/repositories")
    fun getRepositories(
        @Query("since") since: Int
    ): Call<List<Github>>

    @GET("/repos/{user}/{repository}")
    fun getSingleRepository(
        @Path("user") username: String,
        @Path("repository") repositoryName: String
    ): Call<Github>

    @GET("/repos/{user}/{repository}/contributors")
    fun getRepositoryContributors(
        @Path("user") username: String,
        @Path("repository") repositoryName: String
    ): Call<List<Contributor>>
}