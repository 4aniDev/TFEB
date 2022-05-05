package ru.chani.tfeb.domain.usecases

import ru.chani.tfeb.domain.entity.Language
import ru.chani.tfeb.domain.repositories.SharedPreferencesRepository

class PutRecordAboutChosenLanguageUseCase(private val repository: SharedPreferencesRepository) {
    operator fun invoke(language: Language) {
        repository.putRecordAboutChosenLanguage(language)
    }
}