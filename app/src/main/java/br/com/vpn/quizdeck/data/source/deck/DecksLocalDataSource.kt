package br.com.vpn.quizdeck.data.source.deck

import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.domain.model.Deck
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.*

class DecksLocalDataSource(
    private val dao: DecksDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
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

    override suspend fun update(deck: Deck) = withContext(ioDispatcher) {
        dao.update(deck.id, deck.title)
    }

    override suspend fun delete(id: String) = withContext<Unit>(ioDispatcher) {
        dao.delete(UUID.fromString(id))
    }
}