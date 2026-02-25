package jaza.technical.assessment.data.remote.model

import com.google.gson.annotations.SerializedName
import jaza.technical.assessment.data.local.entity.UserDetailEntity

data class UserDetailDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("login")
    val username: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("public_repos")
    val publicRepos: Int
) {
    fun toEntity() = UserDetailEntity(id, username, avatarUrl, bio, followers, publicRepos)
}