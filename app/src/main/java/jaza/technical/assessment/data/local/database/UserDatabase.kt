package jaza.technical.assessment.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import jaza.technical.assessment.data.local.entity.UserDetailEntity
import jaza.technical.assessment.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, UserDetailEntity::class],
    version = 1,
    exportSchema = false
)

abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val userDetailDao: UserDetailDao
}