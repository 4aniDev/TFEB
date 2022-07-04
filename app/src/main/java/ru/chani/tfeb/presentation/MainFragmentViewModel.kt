package ru.chani.tfeb.presentation

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.chani.tfeb.R
import ru.chani.tfeb.data.sms.SmsReader
import ru.chani.tfeb.domain.entity.CardEntity
import ru.chani.tfeb.domain.usecases.GetCardUseCase
import ru.chani.tfeb.domain.usecases.SendSmsUseCase
import ru.chani.tfeb.domain.usecases.UpdateBalanceInTheCardUseCase
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    private val updateBalanceInTheCardUseCase: UpdateBalanceInTheCardUseCase,
    private val getCardUseCase: GetCardUseCase,
    private val sendSmsUseCase: SendSmsUseCase
) : ViewModel() {



    var cardLd = getCardUseCase()

    fun updateBalanceInTheCard(context: Context) {
        viewModelScope.launch {
            val smsReader = SmsReader()
            val updatedBalance = smsReader.readSms(context)

            val cardNumber = cardLd.value?.numberOfCard

            cardNumber?.let {
                updateBalanceInTheCardUseCase(cardNumber, updatedBalance)
            }
        }
    }

    fun sendSms(activity: Activity) {
        val fullCardNumber = cardLd.value?.numberOfCard ?: UNDEFINED_CARD_NUMBER
        val lastFourNumbersOfCardToMessage = getLastFourNumbersOfCard(fullCardNumber)
        sendSmsUseCase(lastFourNumbersOfCardToMessage, activity)
    }

    private fun getLastFourNumbersOfCard(fullCardNumber: String): String {
        val lastIndex = fullCardNumber.lastIndex
        val lastFourNumbersOfCard = StringBuilder()
        for (i in (lastIndex - 3)..lastIndex) {
            val char = fullCardNumber[i]
            lastFourNumbersOfCard.append(char)
        }
        return lastFourNumbersOfCard.toString()
    }

    fun getGreetingPhrase(context: Context): String {
        val personName = cardLd.value?.personName ?: EMPTY_NAME
        val helloWord = context.getString(R.string.hello)
        return "$helloWord $personName"
    }

    fun getCurrency(): String {
        var currency = ""
        cardLd.value?.balance?.let {
            if (it != CardEntity.DEFAULT_CARD_BALANCE) {
                currency = CardEntity.DEFAULT_CARD_CURRENCY
            }
        }
        return currency
    }

    companion object {
        private const val UNDEFINED_CARD_NUMBER = "0000"
        private const val EMPTY_NAME = ""
    }
}