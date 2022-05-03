package ru.chani.tfeb.presentation

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class SettingsViewModel: ViewModel() {

    private var _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun setLocale(activity: Activity, langCode: String) {
        val locale = Locale(langCode)
        Locale.setDefault(locale)

        val resources = activity.resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        finishWork()
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

    companion object {
        const val LANGUAGE_CODE_EN = "en"
        const val LANGUAGE_CODE_RU = "ru"
        const val LANGUAGE_CODE_TM = "tm"
    }
}