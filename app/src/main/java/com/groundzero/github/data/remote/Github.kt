package com.groundzero.github.data.remote

import com.google.gson.annotations.SerializedName

data class Github(
    var name: String,
    @SerializedName("full_name")
    var fullName: String,
    @SerializedName("forks_count")
    var forksCount: Int,
    @SerializedName("stargazers_count")
    var stargazersCount: Int,
    @SerializedName("size")
    var size: Int,
    var owner: Owner
) {
    data class Owner(
        var login: String
    )
}