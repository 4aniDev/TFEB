package ru.chani.tfeb.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.chani.tfeb.data.CardRepositoryImpl
import ru.chani.tfeb.domain.entity.CardEntity
import ru.chani.tfeb.domain.usecases.AddCardUseCase

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val cardRepository = CardRepositoryImpl(application)

    private val addCardUseCase = AddCardUseCase(cardRepository)

    private val defaultCard = CardEntity()

    fun generateDefaultCard() {
        viewModelScope.launch { addCardUseCase(defaultCard) }
    }
}