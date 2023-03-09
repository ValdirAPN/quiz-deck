package br.com.vpn.quizdeck.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey val id: UUID,
    val statement: String,
    val answer: String,
    val deckId: UUID
)