package dev.geanbrandao.minhasdespesas

object CategoryIconUtils {

    fun getCategoryIcon(iconName: String): Int {
        return when (iconName) {
            "ic_house" -> R.drawable.ic_house
            "ic_education" -> R.drawable.ic_education
            "ic_computer" -> R.drawable.ic_computer
            "ic_others" -> R.drawable.ic_others
            "ic_restaurant" -> R.drawable.ic_restaurant
            "ic_healing" -> R.drawable.ic_healing
            "ic_service" -> R.drawable.ic_service
            "ic_supermarket" -> R.drawable.ic_supermarket
            "ic_bus" -> R.drawable.ic_bus
            "ic_store" -> R.drawable.ic_store
            "ic_plane" -> R.drawable.ic_plane
            "ic_credit_card" -> R.drawable.ic_credit_card
            "ic_debit_card" -> R.drawable.ic_debit_card
            "ic_money" -> R.drawable.ic_money
            "ic_recreation" -> R.drawable.ic_recreation
            "ic_gym" -> R.drawable.ic_gym
            else -> R.drawable.ic_tag_default
        }
    }
}