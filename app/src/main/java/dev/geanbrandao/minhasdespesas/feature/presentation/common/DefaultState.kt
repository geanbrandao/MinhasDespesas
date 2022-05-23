package dev.geanbrandao.minhasdespesas.feature.presentation.common

interface DefaultState<out T: Any> {
    val isLoading: Boolean
    val dataList: List<T>
    val data: T?
}
