package ru.chani.tfeb.domain.usecases

import ru.chani.tfeb.domain.entity.Language
import ru.chani.tfeb.domain.repositories.SharedPreferencesRepository
import javax.inject.Inject

class GetRecordAboutChosenLanguageUseCase @Inject constructor (private val repository: SharedPreferencesRepository) {
    operator fun invoke(): Language {
        return repository.getRecordAboutChosenLanguage()
    }
}