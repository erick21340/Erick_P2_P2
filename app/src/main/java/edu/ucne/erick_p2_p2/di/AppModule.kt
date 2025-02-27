package edu.ucne.erick_p2_p2.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.erick_p2_p2.data.local.database.DepositosDB
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object  AppModule {

    @Provides
    @Singleton
    fun provideDepositoDB(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            DepositosDB::class.java,
            "DepositoDB.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideDepositoDao(DepositosDB: DepositosDB) = DepositosDB.DepositosDao()

}