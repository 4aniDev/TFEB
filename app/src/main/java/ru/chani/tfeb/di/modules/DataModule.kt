package ru.chani.tfeb.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import ru.chani.tfeb.data.database.AppDatabase
import ru.chani.tfeb.data.database.CardDao
import ru.chani.tfeb.di.anotations.ApplicationScope

@Module
class DataModule {

    @ApplicationScope
    @Provides
    fun provideCardDao(application: Application): CardDao {
        return AppDatabase.getInstance(application).cardDao()
    }

}