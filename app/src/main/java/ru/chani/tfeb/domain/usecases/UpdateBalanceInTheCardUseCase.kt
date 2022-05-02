package ru.chani.tfeb.domain.usecases

import ru.chani.tfeb.domain.entity.CardEntity
import ru.chani.tfeb.domain.repositories.CardRepository

class UpdateBalanceInTheCardUseCase(private val repository: CardRepository) {
    suspend operator fun invoke(cardNumber: String, newBalance: String) {
        if (newBalance != CardEntity.DEFAULT_CARD_BALANCE) {
            repository.updateBalanceInTheCard(cardNumber, newBalance)
        }
    }
}