package br.com.vpn.quizdeck.presentation.ui.match

import androidx.lifecycle.ViewModel
import br.com.vpn.quizdeck.domain.model.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class MatchUiState(
    val cards: List<Card> = emptyList(),
    val currentCardIndex: Int = 0,
    val showAnswer: Boolean = false,
    val hasFinished: Boolean = false
)

@HiltViewModel
class MatchViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(MatchUiState())
    val uiState: StateFlow<MatchUiState> = _uiState.asStateFlow()

    fun loadCards(cards: List<Card>) {
        _uiState.update { prevState ->
            prevState.copy(
                cards = cards
            )
        }
    }

    private fun hasFinished() = _uiState.value.let {
        it.cards.size <= (it.currentCardIndex + 1)
    }

    fun answer(isCorrect: Boolean) {
        _uiState.update { prevState ->
            val newCardsList = prevState.cards
            newCardsList[prevState.currentCardIndex].isCorrect = isCorrect

            prevState.copy(
                cards = newCardsList,
                currentCardIndex = if (hasFinished().not()) prevState.currentCardIndex.plus(1) else prevState.currentCardIndex,
                showAnswer = false,
                hasFinished = hasFinished()
            )
        }
    }

    fun handleAnswerExibition() {
        _uiState.update { prevState ->
            prevState.copy(
                showAnswer = prevState.showAnswer.not()
            )
        }
    }
}