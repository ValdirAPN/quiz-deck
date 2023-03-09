package br.com.vpn.quizdeck.data.source.card

import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface CardsDataSource {
    suspend fun save(card: Card)
    fun getAllByTopicId(id: String): Flow<ResultData<List<Card>>>
}