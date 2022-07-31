package ru.chani.tfeb.domain.usecases

import ru.chani.tfeb.domain.entity.CardEntity
import ru.chani.tfeb.domain.repositories.CardRepository
import javax.inject.Inject

class GetCardByCardNumberUseCase @Inject constructor (private val cardRepository: CardRepository) {
    suspend operator fun invoke(cardNumber: String): CardEntity {
        return cardRepository.getCardByCardNumber(cardNumber)
    }
}

