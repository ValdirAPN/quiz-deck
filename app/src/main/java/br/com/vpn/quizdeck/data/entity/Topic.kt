package br.com.vpn.quizdeck.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "topic")
data class TopicEntity(
    @PrimaryKey val id: UUID,
    val title: String
)