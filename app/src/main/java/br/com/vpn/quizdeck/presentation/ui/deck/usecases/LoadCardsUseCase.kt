package br.com.vpn.quizdeck.presentation.ui.deck.usecases

import br.com.vpn.quizdeck.data.repository.CardsRepository
import javax.inject.Inject

class LoadCardsUseCase @Inject constructor(
    private val cardsRepository: CardsRepository
) {

    operator fun invoke(deckId: String) = cardsRepository.getAllByDeckId(deckId)
}