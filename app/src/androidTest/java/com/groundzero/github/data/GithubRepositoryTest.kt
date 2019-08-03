package com.groundzero.github.data

import com.google.gson.Gson
import com.groundzero.github.data.remote.Contributor
import com.groundzero.github.data.remote.Github
import com.groundzero.github.data.remote.GithubApi
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubRepositoryTest {

    private lateinit var mockServer: MockWebServer
    private lateinit var retrofit: Retrofit
    private lateinit var githubApi: GithubApi

    @Before
    fun setUp() {
        mockServer = MockWebServer()

        retrofit = Retrofit.Builder()
            .baseUrl(mockServer.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        githubApi = retrofit.create(GithubApi::class.java)
    }

    @Test
    fun github_single_object_response_true() {
        mockServer.enqueue(MockResponse().setBody(getJsonTestObject(getGithubObject())))
        val call = githubApi.getSingleRepository("", "")
        assertEquals("Is single result equal to response data", getGithubObject(), call.execute().body())
    }

    @Test
    fun github_repository_list_response_true() {
        mockServer.enqueue(MockResponse().setBody(getJsonTestObject(getListOfGithubObjects())))
        val call = githubApi.getRepositories(0)
        assertEquals("Is result equal to response data", getListOfGithubObjects(), call.execute().body())
    }

    @Test
    fun github_contributors_list_response_true() {
        mockServer.enqueue(MockResponse().setBody(getJsonTestObject(getListOfContributors())))
        val call = githubApi.getRepositoryContributors("", "")
        assertEquals("Is list of contributors equal to response data", getListOfContributors(), call.execute().body())
    }

    private fun getGithubObject(): Github = Github(
        "Name", "FullName", 3, 4, 1, Github.Owner("LoginName")
    )

    private fun getContributorObject(): Contributor = Contributor("Name", "imageLink")

    private fun getListOfGithubObjects(): List<Github> = listOf(getGithubObject())

    private fun getListOfContributors(): List<Contributor> = listOf(getContributorObject())

    private fun getJsonTestObject(data: Any): String {
        return Gson().toJson(data)
    }
}