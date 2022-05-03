package ru.chani.tfeb.domain.usecases

import ru.chani.tfeb.domain.repositories.SharedPreferencesRepository

class IsDefaultCardUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {

    operator fun invoke() =  sharedPreferencesRepository.isDefaultCard()

}