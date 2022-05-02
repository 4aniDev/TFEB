package ru.chani.tfeb.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardDao {
    @Query("SELECT * FROM tb_card")
    fun getCard(): LiveData<CardDbModel>

    @Query("SELECT * FROM tb_card WHERE numberOfCard = :numberOfCard LIMIT 1")
    suspend fun getCardByCardNumber(numberOfCard: String): CardDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCard(cardDbModel: CardDbModel)

    @Query("UPDATE tb_card SET balance = :newBalance WHERE numberOfCard IN (:cardNumber)")
    suspend fun updateBalanceInTheCard(cardNumber: String, newBalance: String)
}