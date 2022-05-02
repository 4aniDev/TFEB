package ru.chani.tfeb.domain.usecases

import ru.chani.tfeb.domain.entity.CardEntity
import ru.chani.tfeb.domain.repositories.CardRepository

class EditCardUseCase(private val repository: CardRepository) {
    suspend operator fun invoke(cardEntityWithNewData: CardEntity) {
        repository.editCard(cardEntityWithNewData)
    }
}