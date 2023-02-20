package br.com.vpn.quizdeck.presentation.ui.home.usecases

import br.com.vpn.quizdeck.data.repository.TopicsRepository
import javax.inject.Inject

class LoadTopicsUseCase @Inject constructor(
    private val topicsRepository: TopicsRepository
) {

    operator fun invoke() = topicsRepository.getTopicsStream()
}