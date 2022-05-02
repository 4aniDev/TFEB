package ru.chani.tfeb.domain.repositories

import androidx.lifecycle.LiveData
import ru.chani.tfeb.domain.entity.CardEntity

interface CardRepository {

    fun getCard(): LiveData<CardEntity>

    suspend fun getCardByCardNumber(cardNumber: String): CardEntity

    suspend fun addCard(cardEntity: CardEntity)

    suspend fun editCard(cardEntityWithNewData: CardEntity)

    suspend fun updateBalanceInTheCard(cardNumber: String, newBalance: String)

}