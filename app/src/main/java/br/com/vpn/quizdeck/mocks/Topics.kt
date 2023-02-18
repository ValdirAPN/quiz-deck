package br.com.vpn.quizdeck.mocks

import br.com.vpn.quizdeck.domain.model.Topic

fun topics(): List<Topic> {
    return listOf<Topic>(
        Topic("1", "História"),
        Topic("1", "Matemática"),
        Topic("1", "Inglês"),
    )
}