package ru.chani.tfeb.domain.usecases

import ru.chani.tfeb.domain.repositories.SharedPreferencesRepository
import javax.inject.Inject

class PutRecordThatAppWasRunUseCase @Inject constructor (private val repository: SharedPreferencesRepository) {
    operator fun invoke() {
        repository.putRecordThatAppWasRun()
    }
}