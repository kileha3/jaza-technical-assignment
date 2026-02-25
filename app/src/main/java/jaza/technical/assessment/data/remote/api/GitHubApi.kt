package jaza.technical.assessment.data.remote.api

import jaza.technical.assessment.data.remote.model.UserDetailDto
import jaza.technical.assessment.data.remote.model.UserDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("users")
    suspend fun getUsers(@Query("since") since: Int = 0, @Query("per_page") perPage: Int = 10): List<UserDto>

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): UserDetailDto
}