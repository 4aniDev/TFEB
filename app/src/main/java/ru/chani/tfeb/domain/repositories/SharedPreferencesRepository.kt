package ru.chani.tfeb.domain.repositories

import ru.chani.tfeb.domain.entity.Language

interface SharedPreferencesRepository {

    fun putRecordThatAppWasRun()

    fun putRecordThatCardIsNotDefault()

    fun isDefaultCard(): Boolean

    fun didTheAppLaunchEarlier(): Boolean


    fun putRecordAboutChosenLanguage(language: Language)

    fun getRecordAboutChosenLanguage(): Language

}