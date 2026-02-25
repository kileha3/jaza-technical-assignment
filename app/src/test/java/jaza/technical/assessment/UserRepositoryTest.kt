package jaza.technical.assessment

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import jaza.technical.assessment.data.local.database.UserDatabase
import jaza.technical.assessment.data.local.database.UserDetailDao
import jaza.technical.assessment.data.remote.api.GitHubApi
import jaza.technical.assessment.data.remote.model.UserDetailDto
import jaza.technical.assessment.data.repository.UserRepository
import jaza.technical.assessment.domain.repository.UserRepositoryImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {

    // Dependencies
    private lateinit var api: GitHubApi
    private lateinit var db: UserDatabase
    private lateinit var userDetailDao: UserDetailDao
    private lateinit var repository: UserRepository

    private fun testEntity (username: String) = UserDetailDto(
        id = 1,
        username = username,
        avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        bio = "The Star was born at Jaza",
        followers = 9999,
        publicRepos = 8
    )

    @Before
    fun setup() {
        api = mockk(relaxed = true)
        db = mockk(relaxed = true)
        userDetailDao = mockk(relaxed = true)

        coEvery { db.userDetailDao } returns userDetailDao

        repository = UserRepositoryImpl(api, db)
    }

    @Test
    fun `givenUser does not exist on local cache should fetch from remote and cache it`() = runTest {
        val username = "jazaStar"

        val remoteResponse = testEntity(username)


        coEvery { userDetailDao.getByUsername(username) } returns null

        coEvery { api.getUserDetail(username) } returns remoteResponse

        coEvery { userDetailDao.insertAll(any()) } returns Unit

        val flow = repository.getUserDetail(username)

        flow.test {
            val emittedEntity = awaitItem()

            assertEquals(remoteResponse.toEntity().id, emittedEntity.getOrNull()?.id)
            assertEquals(remoteResponse.toEntity().username, emittedEntity.getOrNull()?.username)
            assertEquals(remoteResponse.toEntity().avatarUrl, emittedEntity.getOrNull()?.avatarUrl)
            assertEquals(remoteResponse.toEntity().bio, emittedEntity.getOrNull()?.bio)
            assertEquals(remoteResponse.toEntity().followers, emittedEntity.getOrNull()?.followers)
            assertEquals(remoteResponse.toEntity().publicRepos, emittedEntity.getOrNull()?.publicRepos)

            // Also verify side effects
            coVerify(exactly = 1) { api.getUserDetail(username) }
            coVerify(exactly = 1) { userDetailDao.insertAll(match { it.size == 1 && it[0].username == username }) }

            awaitComplete()
        }
    }

    @Test
    fun `givenUser already exist on local cache should emits cached entity without fetching from remote`() = runTest {
        val username = "jazaStar1"
        val cachedEntity = testEntity(username).toEntity();

        coEvery { userDetailDao.getByUsername(username) } returns cachedEntity

        val flow = repository.getUserDetail(username)

        flow.test {
            val emitted = awaitItem()

            assertEquals(cachedEntity, emitted.getOrNull())
            coVerify(exactly = 0) { api.getUserDetail(any()) }
            coVerify(exactly = 0) { userDetailDao.insertAll(any()) }

            awaitComplete()
        }
    }
}