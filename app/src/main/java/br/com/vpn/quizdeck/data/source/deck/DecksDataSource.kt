package br.com.vpn.quizdeck.data.source.deck

import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.domain.model.Deck
import kotlinx.coroutines.flow.Flow

interface DecksDataSource {
    suspend fun save(deck: Deck)
    fun getAllByTopicId(id: String): Flow<ResultData<List<Deck>>>
}