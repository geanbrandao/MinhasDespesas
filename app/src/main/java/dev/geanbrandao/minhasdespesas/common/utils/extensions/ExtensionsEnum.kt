package dev.geanbrandao.minhasdespesas.common.utils.extensions

import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum.ALL
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum.CURRENT_MONTH
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum.MONTH
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum.NONE
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum.PICKED_DATE
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum.RANGE_DATE
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum.WEEK
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum.YEAR

fun TypeFilterDateEnum.toStringFilter() = when (this) {
    ALL -> "Todos"
    WEEK -> "Semana"
    CURRENT_MONTH -> "Mes atual"
    MONTH -> "Mes"
    YEAR -> "ano"
    PICKED_DATE -> "Data 1"
    RANGE_DATE -> "Data 2"
    NONE -> "Nenhum"
}
