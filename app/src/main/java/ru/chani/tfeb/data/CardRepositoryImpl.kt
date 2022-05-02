package ru.chani.tfeb.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.chani.tfeb.data.database.AppDatabase
import ru.chani.tfeb.data.database.CardMapper
import ru.chani.tfeb.domain.entity.CardEntity
import ru.chani.tfeb.domain.repositories.CardRepository

class CardRepositoryImpl(application: Application) : CardRepository {

    private val cardDao = AppDatabase.getInstance(application).cardDao()
    private val mapper = CardMapper()


    override suspend fun addCard(cardEntity: CardEntity) {
        cardDao.addCard(mapper.mapCardEntityToCardDbModel(cardEntity))
    }

    override suspend fun editCard(cardEntityWithNewData: CardEntity) {
        cardDao.addCard(mapper.mapCardEntityToCardDbModel(cardEntityWithNewData))
    }

    override fun getCard(): LiveData<CardEntity> {
        val cardDbModelLd = cardDao.getCard()
        val carEntityLd = Transformations.map(cardDbModelLd) {
            mapper.mapCardDbModelToCardEntity(it)
        }
        return carEntityLd
    }

    override suspend fun getCardByCardNumber(cardNumber: String): CardEntity {
        val cardDbModel = cardDao.getCardByCardNumber(cardNumber)
        return mapper.mapCardDbModelToCardEntity(cardDbModel)
    }

    override suspend fun updateBalanceInTheCard(cardNumber: String, newBalance: String) {
        cardDao.updateBalanceInTheCard(cardNumber, newBalance)
    }
}