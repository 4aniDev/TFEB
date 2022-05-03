package ru.chani.tfeb.domain.usecases

import ru.chani.tfeb.domain.repositories.SharedPreferencesRepository

class PutRecordThatCardIsNotDefaultUseCase(private val repository: SharedPreferencesRepository) {
    operator fun invoke() {
        repository.putRecordThatCardIsNotDefault()
    }
}