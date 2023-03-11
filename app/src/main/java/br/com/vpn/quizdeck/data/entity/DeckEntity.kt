package br.com.vpn.quizdeck.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "decks",
    foreignKeys = [
        ForeignKey(
            entity = TopicEntity::class,
            childColumns = ["topicId"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class DeckEntity(
    @PrimaryKey val id: UUID,
    val title: String,
    val topicId: UUID
)