package ru.chani.tfeb.presentation

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.chani.tfeb.domain.entity.Language
import ru.chani.tfeb.domain.usecases.DidTheAppLaunchEarlierUseCase
import ru.chani.tfeb.domain.usecases.PutRecordAboutChosenLanguageUseCase
import java.util.*
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val didTheAppLaunchEarlierUseCase: DidTheAppLaunchEarlierUseCase,
    private val putRecordAboutChosenLanguageUseCase: PutRecordAboutChosenLanguageUseCase,
): ViewModel() {

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