package ru.chani.tfeb.domain.entity

data class CardEntity(
    val id: Int = DEFAULT_CARD_ID,
    val numberOfCard: String = DEFAULT_CARD_NUMBER,
    val personName: String = DEFAULT_CARD_PERSON_NAME,
    val personSurname: String = DEFAULT_CARD_PERSON_SURNAME,
    val balance: String = DEFAULT_CARD_BALANCE,
    val status: Boolean = DEFAULT_CARD_STATUS
) {
    companion object {
        private const val DEFAULT_CARD_ID = 1

        const val DEFAULT_CARD_NUMBER = "0000000000000000"
        const val DEFAULT_CARD_PERSON_NAME = "PERSON's NAME"
        const val DEFAULT_CARD_PERSON_SURNAME = "PERSON's'SURNAME"
        const val DEFAULT_CARD_BALANCE = "BALANCE"
        const val DEFAULT_CARD_STATUS = true

        const val DEFAULT_CARD_CURRENCY = "TMT"
    }
}