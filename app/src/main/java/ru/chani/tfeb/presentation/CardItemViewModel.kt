package ru.chani.tfeb.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.chani.tfeb.domain.entity.CardEntity
import ru.chani.tfeb.domain.usecases.EditCardUseCase
import ru.chani.tfeb.domain.usecases.GetCardByCardNumberUseCase
import javax.inject.Inject

class CardItemViewModel @Inject constructor(
    private val getCardByCardNumberUseCase: GetCardByCardNumberUseCase,
    private val editCardUseCase: EditCardUseCase
) : ViewModel() {

    private var _cardItem = MutableLiveData<CardEntity>()
    val cardItem: LiveData<CardEntity>
        get() = _cardItem

    private var _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    private var _errorInputCardNumber = MutableLiveData<Boolean>()
    val errorInputCardNumber: LiveData<Boolean>
        get() = _errorInputCardNumber

    private var _errorInputCardPersonName = MutableLiveData<Boolean>()
    val errorInputCardPersonName: LiveData<Boolean>
        get() = _errorInputCardPersonName

    private var _errorInputCardPersonSurname = MutableLiveData<Boolean>()
    val errorInputCardPersonSurname: LiveData<Boolean>
        get() = _errorInputCardPersonSurname


    fun getCardItemByCardNumber(cardNumber: String) {
        viewModelScope.launch {
            _cardItem.value = getCardByCardNumberUseCase(cardNumber)
        }
    }


    fun editCardItem(inputCardNumber: String, inputPersonName: String, inputPersonSurname: String) {
        val isValidInputData = validateInput(inputCardNumber, inputPersonName, inputPersonSurname)
        if (isValidInputData) {
            _cardItem.value?.let {
                val cardItemWithNewData = it.copy(
                    numberOfCard = inputCardNumber,
                    personName = inputPersonName,
                    personSurname = inputPersonSurname
                )
                viewModelScope.launch {
                    editCardUseCase(cardItemWithNewData)
                }
                finishWork()
            }
        }
    }

    private fun validateInput(
        inputCardNumber: String,
        inputPersonName: String,
        inputPersonSurname: String
    ): Boolean {
        var result = true
        if (inputCardNumber.length != 16) {
            _errorInputCardNumber.value = true
            result = false
        }
        if (inputPersonName.isBlank()) {
            _errorInputCardPersonName.value = true
            result = false
        }
        if (inputPersonSurname.isBlank()) {
            _errorInputCardPersonSurname.value = true
            return false
        }
        return result
    }

    fun resetErrorInputCardNumber() {
        _errorInputCardNumber.value = false
    }

    fun resetErrorInputCardPersonName() {
        _errorInputCardPersonName.value = false
    }

    fun resetErrorInputCardPersonSurname() {
        _errorInputCardPersonSurname.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}