package ru.chani.tfeb.data.sms

import android.app.Activity
import ru.chani.tfeb.domain.repositories.SmsRepository

class SmsRepositoryImpl: SmsRepository {

    override fun sendSms(message: String, activity: Activity) {
        val smsSender = SmsSender()
        smsSender.sendSms(message, activity)
    }
}