package ru.chani.tfeb.presentation

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.chani.tfeb.data.sharedPreferences.SharedPreferencesRepositoryImpl
import ru.chani.tfeb.domain.entity.Language
import ru.chani.tfeb.domain.usecases.DidTheAppLaunchEarlierUseCase
import ru.chani.tfeb.domain.usecases.PutRecordAboutChosenLanguageUseCase
import java.util.*

class SettingsViewModel(application: Application): AndroidViewModel(application) {

    private val repository = SharedPreferencesRepositoryImpl(application)

    private val didTheAppLaunchEarlierUseCase = DidTheAppLaunchEarlierUseCase(repository)
    private val putRecordAboutChosenLanguageUseCase = PutRecordAboutChosenLanguageUseCase(repository)

    private var _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun setLocale(activity: Activity, language: Language) {
        val langCode = language.name
        val locale = Locale(langCode)
        Locale.setDefault(locale)

        val resources = activity.resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        putRecordAboutChosenLanguageUseCase(language)

        finishWork()
    }

    fun didTheAppLaunchEarlier() = didTheAppLaunchEarlierUseCase()

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}