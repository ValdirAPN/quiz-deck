package br.com.vpn.quizdeck.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "cards",
    foreignKeys = [
        ForeignKey(
            entity = DeckEntity::class,
            childColumns = ["deckId"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class CardEntity(
    @PrimaryKey val id: UUID,
    val statement: String,
    val answer: String,
    val deckId: UUID
)