package io.github.sooakim.network.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SAAuthRequest(
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("password")
    @Expose
    val password: String
) : SARequest