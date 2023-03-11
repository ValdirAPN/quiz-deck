package br.com.vpn.quizdeck.data.source.card

import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.domain.model.Card
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.*

class CardsLocalDataSource(
    private val dao: CardsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CardsDataSource {
    override suspend fun save(card: Card) {
        dao.insert(card.toEntity())
    }

    override fun getAllByTopicId(id: String): Flow<ResultData<List<Card>>> {
        return dao.getAllByDeckId(UUID.fromString(id)).map { cards ->
            ResultData.Success(
                cards.map { card ->
                    Card.fromEntity(card)
                }
            )
        }
    }

    override suspend fun update(card: Card) = withContext(ioDispatcher) {
        dao.update(card.id, card.statement, card.answer)
    }

    override suspend fun delete(id: String) = withContext<Unit>(ioDispatcher) {
        dao.delete(UUID.fromString(id))
    }
}