package dev.geanbrandao.minhasdespesas.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.geanbrandao.minhasdespesas.core.database.AppDatabase
import dev.geanbrandao.minhasdespesas.core.database.AppDatabase.Companion.DB_NAME
import dev.geanbrandao.minhasdespesas.core.database.DatabaseDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DB_NAME,
        ).build()
    }

    @Provides
    fun provideDatabaseDao(appDatabase: AppDatabase): DatabaseDao {
        return appDatabase.databaseDao
    }
}