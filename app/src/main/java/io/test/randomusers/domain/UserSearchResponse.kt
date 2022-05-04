package io.test.randomusers.domain


import com.google.gson.annotations.SerializedName
import io.test.randomusers.data.model.Info
import io.test.randomusers.data.model.User
import io.test.randomusers.domain.model.RandomUser

data class UserSearchResponse(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val results: List<User>
)

fun User.toRandomUser(): RandomUser {
    return RandomUser(
        id = dob,
        name = name.last,
        firstname = name.first,
        email = email,
        gender = gender,
        location = location.street + " " + location.city + " " + location.postcode + " " + location.state,
        nationality = nat,
        phone = phone,
        picture = picture.medium,
    )
}