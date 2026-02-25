package jaza.technical.assessment.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jaza.technical.assessment.data.local.entity.UserDetailEntity

@Dao
interface UserDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserDetailEntity>)

    @Query("SELECT * FROM user_details WHERE username = :username")
    suspend fun getByUsername(username: String): UserDetailEntity?
}