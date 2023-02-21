package br.com.vpn.quizdeck.domain.model

import br.com.vpn.quizdeck.data.entity.DeckEntity
import java.util.UUID

class Deck(
    val id: UUID,
    val title: String,
    val topicId: UUID
) {
    fun toEntity() = DeckEntity(
        id = id,
        title = title,
        topicId = topicId
    )

    companion object {
        fun fromEntity(deckEntity: DeckEntity) = Deck(
            id = deckEntity.id,
            title = deckEntity.title,
            topicId = deckEntity.topicId
        )
    }
}