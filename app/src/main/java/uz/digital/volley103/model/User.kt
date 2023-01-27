package uz.digital.volley103.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val login: String,
    @SerializedName("avatar_url")
    val image: String,
    val url: String
)