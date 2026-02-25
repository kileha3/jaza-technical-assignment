package jaza.technical.assessment.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import jaza.technical.assessment.data.local.database.UserDatabase
import jaza.technical.assessment.data.local.entity.UserEntity
import jaza.technical.assessment.data.remote.api.GitHubApi
import jaza.technical.assessment.utils.ExceptionUtil.networkException

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val api: GitHubApi,
    private val db: UserDatabase
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        return try {
            val since = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = false)
                    lastItem.id.toInt()
                }
            }

            val users = api.getUsers(
                since = since,
                perPage = state.config.pageSize
            )

            db.withTransaction {
                db.userDao.insertAll(users.map { it.toEntity() })
            }

            MediatorResult.Success(endOfPaginationReached = users.isEmpty())
        } catch (exception: Exception) {
            MediatorResult.Error(networkException(exception))
        }
    }
}
