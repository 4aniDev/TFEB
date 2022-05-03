package ru.chani.tfeb.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.chani.tfeb.data.CardRepositoryImpl
import ru.chani.tfeb.data.sharedPreferences.SharedPreferencesRepositoryImpl
import ru.chani.tfeb.domain.entity.CardEntity
import ru.chani.tfeb.domain.usecases.*

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val cardRepository = CardRepositoryImpl(application)
    private val shPreferencesRepository = SharedPreferencesRepositoryImpl(application)

    private val addCardUseCase = AddCardUseCase(cardRepository)
    private val putRecordThatAppWasRunUseCase = PutRecordThatAppWasRunUseCase(shPreferencesRepository)
    private val putRecordThatCardIsNotDefaultUseCase = PutRecordThatCardIsNotDefaultUseCase(shPreferencesRepository)
    private val isDefaultCardUseCae = IsDefaultCardUseCase(shPreferencesRepository)
    private val didTheAppLaunchEarlierUseCase = DidTheAppLaunchEarlierUseCase(shPreferencesRepository)

    private val defaultCard = CardEntity()



    fun generateDefaultCard() {
        viewModelScope.launch { addCardUseCase(defaultCard) }
    }

    fun putRecordThatAppWasRun() {
        putRecordThatAppWasRunUseCase()
    }

    fun putRecordThatCardIsNotDefault() {
        putRecordThatCardIsNotDefaultUseCase()
    }

    fun isDefaultCard(): Boolean {
        return isDefaultCardUseCae()
    }

    fun didTheAppLaunchEarlier() = didTheAppLaunchEarlierUseCase()

}