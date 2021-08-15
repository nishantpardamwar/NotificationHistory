package com.nishantpardamwar.notificationhistory.di

import android.content.Context
import androidx.room.Room
import com.nishantpardamwar.notificationhistory.database.AppDatabase
import com.nishantpardamwar.notificationhistory.repo.Repo
import com.nishantpardamwar.notificationhistory.repo.RepoImpl
import com.nishantpardamwar.notificationhistory.repo.datastore.LocalDataStore
import com.nishantpardamwar.notificationhistory.repo.datastore.LocalDataStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "appdb").fallbackToDestructiveMigration().allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideLocalDataStore(db: AppDatabase): LocalDataStore {
        return LocalDataStoreImpl(db)
    }

    @Singleton
    @Provides
    fun provideRepo(localDataStore: LocalDataStore): Repo {
        return RepoImpl(localDataStore)
    }
}