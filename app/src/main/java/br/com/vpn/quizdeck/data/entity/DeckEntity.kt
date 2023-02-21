package br.com.vpn.quizdeck.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "decks")
data class DeckEntity(
    @PrimaryKey val id: UUID,
    val title: String,
    val topicId: UUID
)