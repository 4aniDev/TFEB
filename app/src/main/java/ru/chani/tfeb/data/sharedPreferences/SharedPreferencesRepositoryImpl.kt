package ru.chani.tfeb.data.sharedPreferences

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import ru.chani.tfeb.domain.repositories.SharedPreferencesRepository

class SharedPreferencesRepositoryImpl(context: Context) : SharedPreferencesRepository {

    private val shpAppRunInfo = context.getSharedPreferences(
        SHARED_PREFERENCE_APP_RUN_INFORMATION,
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

    companion object {
        private const val SHARED_PREFERENCE_APP_RUN_INFORMATION =
            "SHARED_PREFERENCE_APP_RUN_INFORMATION"


        private const val SHARED_PREFERENCE_KEY_DID_APP_LAUNCH = "DID APP LAUNCHED EARLIER?"
        private const val SHARED_PREFERENCE_VALUE_APP_WAS_LAUNCHED = true
        private const val SHARED_PREFERENCE_VALUE_APP_WAS_LAUNCHED_DEFAULT = false

        private const val SHARED_PREFERENCE_KEY_IS_DEFAULT_CARD = "KEY for CARD_INFO"
        private const val SHARED_PREFERENCE_VALUE_CARD_IS_NOT_DEFAULT = false
        private const val SHARED_PREFERENCE_VALUE_CARD_IS_DEFAULT = true
    }
}