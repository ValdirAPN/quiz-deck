package br.com.vpn.quizdeck.data.source.deck

import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.domain.model.Deck
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class DecksLocalDataSource(
    private val dao: DecksDao
) : DecksDataSource {
    override suspend fun save(deck: Deck) {
        dao.insert(deck.toEntity())
    }

    override fun getAllByTopicId(id: String): Flow<ResultData<List<Deck>>> {
        return dao.getAllByTopicId(UUID.fromString(id)).map { decks ->
            ResultData.Success(
                decks.map { deck ->
                    Deck.fromEntity(deck)
                }
            )
        }
    }
}