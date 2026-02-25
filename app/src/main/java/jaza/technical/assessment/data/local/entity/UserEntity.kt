package jaza.technical.assessment.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import jaza.technical.assessment.domain.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: Long,
    val username: String,
    val avatarUrl: String,
    val type: String
) {
    fun toDomain() = User(id, username, avatarUrl, type)
}