package ru.chani.tfeb.data.sms

import android.app.Activity
import ru.chani.tfeb.domain.repositories.SmsRepository
import javax.inject.Inject

class SmsRepositoryImpl @Inject constructor(): SmsRepository {

    override fun sendSms(message: String, activity: Activity) {
        val smsSender = SmsSender()
        smsSender.sendSms(message, activity)
    }
}