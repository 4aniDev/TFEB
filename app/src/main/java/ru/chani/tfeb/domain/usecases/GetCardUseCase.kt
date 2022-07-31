package ru.chani.tfeb.domain.usecases

import androidx.lifecycle.LiveData
import ru.chani.tfeb.domain.entity.CardEntity
import ru.chani.tfeb.domain.repositories.CardRepository
import javax.inject.Inject

class GetCardUseCase @Inject constructor(
    private val cardRepository: CardRepository
    ) {
    operator fun invoke(): LiveData<CardEntity> {
        return cardRepository.getCard()
    }
}