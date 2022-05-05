package ru.chani.tfeb.presentation

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.chani.tfeb.data.CardRepositoryImpl
import ru.chani.tfeb.data.sharedPreferences.SharedPreferencesRepositoryImpl
import ru.chani.tfeb.domain.entity.CardEntity
import ru.chani.tfeb.domain.entity.Language
import ru.chani.tfeb.domain.usecases.*
import java.util.*

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val cardRepository = CardRepositoryImpl(application)
    private val shPreferencesRepository = SharedPreferencesRepositoryImpl(application)


    private val addCardUseCase = AddCardUseCase(cardRepository)

    private val putRecordThatAppWasRunUseCase = PutRecordThatAppWasRunUseCase(shPreferencesRepository)
    private val putRecordThatCardIsNotDefaultUseCase = PutRecordThatCardIsNotDefaultUseCase(shPreferencesRepository)
    private val isDefaultCardUseCae = IsDefaultCardUseCase(shPreferencesRepository)
    private val didTheAppLaunchEarlierUseCase = DidTheAppLaunchEarlierUseCase(shPreferencesRepository)

    private val getRecordAboutChosenLanguageUseCase = GetRecordAboutChosenLanguageUseCase(shPreferencesRepository)


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

    fun setLanguage(activity: Activity) {
        val language = getRecordAboutChosenLanguageUseCase()
        setLocale(activity, language)
    }

    private fun setLocale(activity: Activity, language: Language) {
        val langCode = language.name
        val locale = Locale(langCode)
        Locale.setDefault(locale)

        val resources = activity.resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}