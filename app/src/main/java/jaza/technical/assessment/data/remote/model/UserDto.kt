package jaza.technical.assessment.data.remote.model

import com.google.gson.annotations.SerializedName
import jaza.technical.assessment.data.local.entity.UserEntity

data class UserDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("login")
    val username: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("type")
    val type: String
){
    fun toEntity() = UserEntity(
        id = id,
        username = username,
        avatarUrl = avatarUrl,
        type = type
    )
}