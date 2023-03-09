package br.com.vpn.quizdeck.data.source.card

import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.domain.model.Card
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class CardsLocalDataSource(
    private val dao: CardsDao
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
}