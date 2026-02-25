package jaza.technical.assessment.data.remote.api

import jaza.technical.assessment.data.remote.model.UserDetailDto
import jaza.technical.assessment.data.remote.model.UserDto
import jaza.technical.assessment.utils.Constants.PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("users")
    suspend fun getUsers(@Query("since") since: Int = 0, @Query("per_page") perPage: Int = PAGE_SIZE): List<UserDto>

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): UserDetailDto
}