package ru.chani.tfeb.data.database

import ru.chani.tfeb.domain.entity.CardEntity

class CardMapper {
    fun mapCardEntityToCardDbModel(cardEntity: CardEntity) = CardDbModel(
        id = cardEntity.id,
        numberOfCard = cardEntity.numberOfCard,
        personName = cardEntity.personName,
        personSurname = cardEntity.personSurname,
        balance = cardEntity.balance,
        status = cardEntity.status
    )

    fun mapCardDbModelToCardEntity(cardDbModel: CardDbModel) = CardEntity(
        id = cardDbModel.id,
        numberOfCard = cardDbModel.numberOfCard,
        personName = cardDbModel.personName,
        personSurname = cardDbModel.personSurname,
        balance = cardDbModel.balance,
        status = cardDbModel.status
    )

}