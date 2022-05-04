package io.test.randomusers.data.model


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("city")
    val city: String,
    @SerializedName("postcode")
    val postcode: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("street")
    val street: String
)