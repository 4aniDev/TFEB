package ru.chani.tfeb.domain.repositories

import android.app.Activity

interface SmsRepository {

    fun sendSms(lastFourNumbersOfCardToMessage: String, activity: Activity)
}