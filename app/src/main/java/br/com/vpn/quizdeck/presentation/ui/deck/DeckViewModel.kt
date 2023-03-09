package br.com.vpn.quizdeck.presentation.ui.deck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.domain.model.Card
import br.com.vpn.quizdeck.presentation.ui.deck.usecases.CreateCardUseCase
import br.com.vpn.quizdeck.presentation.ui.deck.usecases.LoadCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


data class DeckUiState(
    val cards: List<Card> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class DeckViewModel @Inject constructor(
    private val loadCardsUseCase: LoadCardsUseCase,
    private val createCardUseCase: CreateCardUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DeckUiState())
    val uiState: StateFlow<DeckUiState> = _uiState.asStateFlow()

    fun addCard(card: Card) {
        viewModelScope.launch {
            createCardUseCase.invoke(card)
        }
    }

    fun loadCards(deckId: String) {
        viewModelScope.launch {
            loadCardsUseCase(deckId).collect { result ->
                when (result) {
                    is ResultData.Success -> {
                        _uiState.update { prevState ->
                            prevState.copy(
                                cards = result.data,
                                isLoading = false
                            )
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}