package com.groundzero.github.data.remote

import com.google.gson.annotations.SerializedName

data class Contributor(
    @SerializedName("login")
    var name: String,
    @SerializedName("avatar_url")
    var image: String
)