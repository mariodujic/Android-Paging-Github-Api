package com.groundzero.github.data.remote

import retrofit2.Call

const val BASE_URL = "https://api.github.com"

open class GithubRepository(private val database: GithubDatabase) {

    private fun getGithubApi() = database.getDatabase(BASE_URL).create(GithubApi::class.java)

    fun getRepositories(since: Int): Call<List<Github>> = getGithubApi().getRepositories(since)

    fun getSingleRepository(username: String, repositoryName: String): Call<Github> =
        getGithubApi().getSingleRepository(username, repositoryName)

    fun getRepositoryContributors(username: String, repositoryName: String): Call<List<Contributor>> =
        getGithubApi().getRepositoryContributors(username, repositoryName)
}