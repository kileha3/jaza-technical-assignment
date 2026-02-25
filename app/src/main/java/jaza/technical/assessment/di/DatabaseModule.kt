package jaza.technical.assessment.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jaza.technical.assessment.data.local.database.UserDao
import jaza.technical.assessment.data.local.database.UserDatabase
import jaza.technical.assessment.data.local.database.UserDetailDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "github_users.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: UserDatabase): UserDao {
        return database.userDao
    }

    @Provides
    @Singleton
    fun provideUserDetailsDao(database: UserDatabase): UserDetailDao {
        return database.userDetailDao
    }
}