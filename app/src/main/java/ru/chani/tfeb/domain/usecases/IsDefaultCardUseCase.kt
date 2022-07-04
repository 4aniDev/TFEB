package ru.chani.tfeb.domain.usecases

import ru.chani.tfeb.domain.repositories.SharedPreferencesRepository
import javax.inject.Inject

class IsDefaultCardUseCase @Inject constructor (private val sharedPreferencesRepository: SharedPreferencesRepository) {

    operator fun invoke() =  sharedPreferencesRepository.isDefaultCard()

}