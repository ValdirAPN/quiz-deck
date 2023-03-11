package br.com.vpn.quizdeck.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vpn.quizdeck.data.ResultData
import br.com.vpn.quizdeck.domain.model.Topic
import br.com.vpn.quizdeck.presentation.ui.home.usecases.CreateTopicUseCase
import br.com.vpn.quizdeck.presentation.ui.home.usecases.DeleteTopicUseCase
import br.com.vpn.quizdeck.presentation.ui.home.usecases.EditTopicUseCase
import br.com.vpn.quizdeck.presentation.ui.home.usecases.LoadTopicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val topics: List<Topic>
) {
    companion object {
        fun createEmpty(): HomeUiState {
            return HomeUiState(
                topics = emptyList()
            )
        }
    }
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadTopicsUseCase: LoadTopicsUseCase,
    private val createTopicUseCase: CreateTopicUseCase,
    private val deleteTopicUseCase: DeleteTopicUseCase,
    private val editTopicUseCase: EditTopicUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState.createEmpty())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadTopics()
    }

    private fun loadTopics() {
        viewModelScope.launch {
            loadTopicsUseCase.invoke().collect { result ->
                when (result) {
                    is ResultData.Success -> {
                        _uiState.update { prevState ->
                            prevState.copy(
                                topics = result.data
                            )
                        }
                    }
                    is ResultData.Error -> {}
                }
            }
        }
    }

    fun addTopic(topic: Topic) {
        viewModelScope.launch {
            createTopicUseCase.invoke(topic = topic)
        }
    }

    fun updateTopic(topic: Topic) {
        viewModelScope.launch {
            editTopicUseCase.invoke(topic = topic)
        }
    }

    fun deleteTopic(topic: Topic) {
        viewModelScope.launch {
            deleteTopicUseCase.invoke(topicId = topic.id.toString())
        }
    }
}