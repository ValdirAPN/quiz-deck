package br.com.vpn.quizdeck.domain.model

import br.com.vpn.quizdeck.data.entity.TopicEntity
import java.io.Serializable
import java.util.UUID

data class Topic(
    val id: UUID,
    val title: String,
) : Serializable {
    fun toEntity() = TopicEntity(
        id = id,
        title = title
    )

    companion object {
        fun fromEntity(entity: TopicEntity) = Topic(
            id = entity.id,
            title = entity.title
        )
    }
}
