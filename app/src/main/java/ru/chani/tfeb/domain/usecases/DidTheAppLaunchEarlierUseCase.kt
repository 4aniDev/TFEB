package ru.chani.tfeb.domain.usecases

import ru.chani.tfeb.domain.repositories.SharedPreferencesRepository

class DidTheAppLaunchEarlierUseCase(private val repository: SharedPreferencesRepository) {

    operator fun invoke() = repository.didTheAppLaunchEarlier()

}