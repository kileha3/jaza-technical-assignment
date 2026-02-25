package jaza.technical.assessment.data.local.entity

import androidx.room.Entity
import jaza.technical.assessment.domain.model.UserDetail

@Entity(tableName = "user_details", primaryKeys = ["id", "username"])
data class UserDetailEntity(
    val id: Long,
    val username: String,
    val avatarUrl: String,
    val bio: String?,
    val followers: Int,
    val publicRepos: Int
) {
    fun toDomain() = UserDetail(id, username, avatarUrl, bio, followers, publicRepos)
}