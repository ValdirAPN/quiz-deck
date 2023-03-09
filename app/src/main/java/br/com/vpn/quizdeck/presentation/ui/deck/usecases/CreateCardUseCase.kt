package br.com.vpn.quizdeck.presentation.ui.deck.usecases

import br.com.vpn.quizdeck.data.repository.CardsRepository
import br.com.vpn.quizdeck.domain.model.Card
import javax.inject.Inject

class CreateCardUseCase @Inject constructor(
    private val cardsRepository: CardsRepository
) {

    suspend operator fun invoke(card: Card) {
        cardsRepository.save(card)
    }
}