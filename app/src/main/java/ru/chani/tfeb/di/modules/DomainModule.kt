package ru.chani.tfeb.di.modules

import dagger.Binds
import dagger.Module
import ru.chani.tfeb.data.CardRepositoryImpl
import ru.chani.tfeb.data.sharedPreferences.SharedPreferencesRepositoryImpl
import ru.chani.tfeb.data.sms.SmsRepositoryImpl
import ru.chani.tfeb.domain.repositories.CardRepository
import ru.chani.tfeb.domain.repositories.SharedPreferencesRepository
import ru.chani.tfeb.domain.repositories.SmsRepository

@Module
interface DomainModule {

    @Binds
    fun bindCardRepository(impl: CardRepositoryImpl): CardRepository

    @Binds
    fun bindSharedPreferencesRepository(impl: SharedPreferencesRepositoryImpl): SharedPreferencesRepository

    @Binds
    fun bindSmsRepository(impl: SmsRepositoryImpl): SmsRepository

}