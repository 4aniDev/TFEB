package ru.chani.tfeb.domain.usecases

import android.app.Activity
import ru.chani.tfeb.domain.repositories.SmsRepository

class SendSmsUseCase(private val smsRepository: SmsRepository) {

    operator fun invoke(lastFourNumbersOfCardToMessage: String, activity: Activity) {
        smsRepository.sendSms( lastFourNumbersOfCardToMessage, activity)
    }
}