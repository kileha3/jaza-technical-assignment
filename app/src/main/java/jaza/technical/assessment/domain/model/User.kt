package jaza.technical.assessment.domain.model

data class User(
    val id: Long,
    val username: String,
    val avatarUrl: String,
    val type: String
)