package br.com.vpn.quizdeck.domain.model

import br.com.vpn.quizdeck.data.entity.CardEntity
import java.io.Serializable
import java.util.*

data class Card(
    val id: UUID,
    val statement: String,
    val answer: String,
    val deckId: UUID,
    var isCorrect: Boolean = true
): Serializable {
    fun toEntity() = CardEntity(
        id = id,
        statement = statement,
        answer = answer,
        deckId = deckId
    )

    companion object {
        fun fromEntity(cardEntity: CardEntity) = Card(
            id = cardEntity.id,
            statement = cardEntity.statement,
            answer = cardEntity.answer,
            deckId = cardEntity.deckId
        )
    }
}