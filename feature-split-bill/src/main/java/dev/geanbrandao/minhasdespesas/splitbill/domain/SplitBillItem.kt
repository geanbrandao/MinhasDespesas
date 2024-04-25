package dev.geanbrandao.minhasdespesas.splitbill.domain

data class SplitBillItem(
    val itemId: Long,
    val ownerName: String,
    val name: String,
    val value: Float,
) {

}
