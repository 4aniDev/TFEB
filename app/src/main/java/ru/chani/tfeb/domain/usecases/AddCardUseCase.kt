package ru.chani.tfeb.domain.usecases

import ru.chani.tfeb.domain.entity.CardEntity
import ru.chani.tfeb.domain.repositories.CardRepository

class AddCardUseCase(private val repository: CardRepository) {
    suspend operator fun invoke(cardEntity: CardEntity) {
        repository.addCard(cardEntity)
    }
}