package br.com.vpn.quizdeck.presentation.ui.topic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.domain.model.Deck
import br.com.vpn.quizdeck.presentation.ui.topic.usecases.CreateDecksUseCase
import br.com.vpn.quizdeck.presentation.ui.topic.usecases.LoadDecksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TopicUiState(
    val decks: List<Deck> = emptyList(),
    val isLoading: Boolean = true,
)

@HiltViewModel
class TopicViewModel @Inject constructor(
    private val createDecksUseCase: CreateDecksUseCase,
    private val loadDecksUseCase: LoadDecksUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TopicUiState())
    val uiState: StateFlow<TopicUiState> = _uiState.asStateFlow()

    fun addDeck(deck: Deck) {
        viewModelScope.launch {
            createDecksUseCase.invoke(deck)
        }
    }

    fun loadDecks(topicId: String) {
        viewModelScope.launch {
            loadDecksUseCase(topicId).collect { result ->
                when (result) {
                    is ResultData.Success -> {
                        _uiState.update { prevState ->
                            prevState.copy(
                                decks = result.data,
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