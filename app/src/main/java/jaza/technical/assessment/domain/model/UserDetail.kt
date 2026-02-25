package jaza.technical.assessment.domain.model

data class UserDetail(
    var id: Long,
    val username: String,
    val avatarUrl: String,
    val bio: String?,
    val followers: Int,
    val publicRepos: Int
)