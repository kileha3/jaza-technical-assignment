package jaza.technical.assessment.data.repository

import androidx.paging.PagingData
import jaza.technical.assessment.data.local.entity.UserDetailEntity
import jaza.technical.assessment.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<PagingData<UserEntity>>
    suspend fun getUserDetail(username: String): Flow<Result<UserDetailEntity>>
    suspend fun refreshUsers(page: Int = 1)
}