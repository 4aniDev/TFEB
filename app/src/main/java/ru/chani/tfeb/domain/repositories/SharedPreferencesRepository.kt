package ru.chani.tfeb.domain.repositories

interface SharedPreferencesRepository {

    fun putRecordThatAppWasRun()

    fun putRecordThatCardIsNotDefault()

    fun isDefaultCard(): Boolean

    fun didTheAppLaunchEarlier(): Boolean

}