package ru.chani.tfeb.presentation

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.chani.tfeb.domain.entity.CardEntity
import ru.chani.tfeb.domain.entity.Language
import ru.chani.tfeb.domain.usecases.*
import java.util.*
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val addCardUseCase: AddCardUseCase,
    private val putRecordThatAppWasRunUseCase: PutRecordThatAppWasRunUseCase,
    private val putRecordThatCardIsNotDefaultUseCase: PutRecordThatCardIsNotDefaultUseCase,
    private val isDefaultCardUseCae: IsDefaultCardUseCase,
    private val didTheAppLaunchEarlierUseCase: DidTheAppLaunchEarlierUseCase,
    private val getRecordAboutChosenLanguageUseCase: GetRecordAboutChosenLanguageUseCase,
) : ViewModel() {

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