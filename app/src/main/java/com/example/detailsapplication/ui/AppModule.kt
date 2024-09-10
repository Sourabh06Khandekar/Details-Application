package com.example.detailsapplication.ui

import android.content.Context
import androidx.room.Room
import com.example.detailsapplication.ui.database.UserDetailsDao
import com.example.detailsapplication.ui.database.UserDetailsDatabase
import com.example.detailsapplication.ui.database.UserDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUserDetailsDatabase(@ApplicationContext app: Context): UserDetailsDatabase {
        return Room.databaseBuilder(
            app,
            UserDetailsDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
    @Provides
    @Singleton
    fun provideUserDetailsDao(db: UserDetailsDatabase):UserDetailsDao {
        return db.getUserDetailsDao()
    }

    @Provides
    @Singleton
    fun provideUserDetailsRepository(userDetailsDao: UserDetailsDao) = UserDetailsRepository(userDetailsDao)


}