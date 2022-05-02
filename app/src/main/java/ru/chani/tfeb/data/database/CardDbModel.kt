package ru.chani.tfeb.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_card")
data class CardDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val numberOfCard: String,
    val personName: String,
    val personSurname: String,
    val balance: String,
    val status: Boolean)
