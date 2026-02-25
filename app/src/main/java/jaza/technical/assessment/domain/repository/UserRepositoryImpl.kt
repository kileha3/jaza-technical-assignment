package jaza.technical.assessment.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import jaza.technical.assessment.data.local.database.UserDatabase
import jaza.technical.assessment.data.local.entity.UserDetailEntity
import jaza.technical.assessment.data.local.entity.UserEntity
import jaza.technical.assessment.data.remote.api.GitHubApi
import jaza.technical.assessment.data.repository.UserRemoteMediator
import jaza.technical.assessment.data.repository.UserRepository
import jaza.technical.assessment.utils.Constants.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: GitHubApi, private val db: UserDatabase
) : UserRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getUsers(): Flow<PagingData<UserEntity>> {
        val pagingSourceFactory = { db.userDao.getPagedUsers() }
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = UserRemoteMediator(api, db),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun getUserDetail(username: String): Flow<Result<UserDetailEntity>> = flow {
        val localUserDetail = db.userDetailDao.getByUsername(username)
        if (localUserDetail != null) {
            emit(Result.success(localUserDetail))
        } else {
            try {
                val remoteUserDetail = api.getUserDetail(username).toEntity()
                db.userDetailDao.insertAll(listOf(remoteUserDetail))
                emit(Result.success(remoteUserDetail))
            } catch (e: Exception) {
                emit(Result.failure(Exception("Failed to load user details")))
            }
        }
    }

    override suspend fun refreshUsers(page: Int) {
        val users = api.getUsers(since = page, perPage = PAGE_SIZE).map { it.toEntity() }
        db.userDao.insertAll(users)
    }
}