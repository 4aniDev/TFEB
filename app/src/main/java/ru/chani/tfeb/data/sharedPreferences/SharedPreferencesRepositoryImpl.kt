package ru.chani.tfeb.data.sharedPreferences

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import ru.chani.tfeb.domain.entity.Language
import ru.chani.tfeb.domain.repositories.SharedPreferencesRepository
import javax.inject.Inject

class SharedPreferencesRepositoryImpl @Inject constructor (application: Application) : SharedPreferencesRepository {

    private val shpAppRunInfo = application.getSharedPreferences(
        SHARED_PREFERENCE_APP_RUN_INFORMATION,
        AppCompatActivity.MODE_PRIVATE
    )

    private val shpLanguageSettings = application.getSharedPreferences(
        SHARED_PREFERENCE_APP_LANGUAGE_SETTINGS,
        AppCompatActivity.MODE_PRIVATE
    )


    override fun putRecordThatAppWasRun() {
        val editor = shpAppRunInfo.edit()
        editor.putBoolean(
            SHARED_PREFERENCE_KEY_DID_APP_LAUNCH,
            SHARED_PREFERENCE_VALUE_APP_WAS_LAUNCHED
        )
        editor.apply()
    }

    override fun putRecordThatCardIsNotDefault() {
        shpAppRunInfo.edit().apply {
            putBoolean(
                SHARED_PREFERENCE_KEY_IS_DEFAULT_CARD,
                SHARED_PREFERENCE_VALUE_CARD_IS_NOT_DEFAULT
            )
        }.apply()
    }

    override fun isDefaultCard(): Boolean {
        return shpAppRunInfo.getBoolean(
            SHARED_PREFERENCE_KEY_IS_DEFAULT_CARD,
            SHARED_PREFERENCE_VALUE_CARD_IS_DEFAULT
        )
    }

    override fun didTheAppLaunchEarlier(): Boolean {
        return shpAppRunInfo.getBoolean(
            SHARED_PREFERENCE_KEY_DID_APP_LAUNCH,
            SHARED_PREFERENCE_VALUE_APP_WAS_LAUNCHED_DEFAULT
        )
    }


    override fun putRecordAboutChosenLanguage(language: Language) {
        val stringNameOfChosenLanguage = language.name
        shpLanguageSettings.edit().apply {
            putString(SHARED_PREFERENCE_KEY_LANGUAGE, stringNameOfChosenLanguage)
        }.apply()
    }


    override fun getRecordAboutChosenLanguage(): Language {
        val languageFromShPref = shpLanguageSettings.getString(
            SHARED_PREFERENCE_KEY_LANGUAGE,
            SHARED_PREFERENCE_DEFAULT_LANGUAGE
        )
        return when (languageFromShPref) {
            Language.RU.name -> Language.RU
            Language.TK.name -> Language.TK
            else -> Language.EN
        }
    }

    companion object {
        private const val SHARED_PREFERENCE_APP_RUN_INFORMATION =
            "SHARED_PREFERENCE_APP_RUN_INFORMATION"
        private const val SHARED_PREFERENCE_APP_LANGUAGE_SETTINGS = "S_P_APP_LANGUAGE_SETTINGS"

        private const val SHARED_PREFERENCE_KEY_DID_APP_LAUNCH = "DID APP LAUNCHED EARLIER?"
        private const val SHARED_PREFERENCE_VALUE_APP_WAS_LAUNCHED = true
        private const val SHARED_PREFERENCE_VALUE_APP_WAS_LAUNCHED_DEFAULT = false

        private const val SHARED_PREFERENCE_KEY_IS_DEFAULT_CARD = "KEY for CARD_INFO"
        private const val SHARED_PREFERENCE_VALUE_CARD_IS_NOT_DEFAULT = false
        private const val SHARED_PREFERENCE_VALUE_CARD_IS_DEFAULT = true

        private const val SHARED_PREFERENCE_KEY_LANGUAGE = "SP_KEY_LANGUAGE"
        private const val SHARED_PREFERENCE_DEFAULT_LANGUAGE = "EN"
    }
}