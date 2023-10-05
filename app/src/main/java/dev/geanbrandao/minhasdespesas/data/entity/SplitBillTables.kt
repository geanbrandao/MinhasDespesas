package dev.geanbrandao.minhasdespesas.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class SplitBillUserDb(
    @PrimaryKey(autoGenerate = true)
    val userId: Long,
    val name: String,
)

@Entity
data class SplitBillItemDb(
    @PrimaryKey(autoGenerate = true)
    val itemId: Long,
    val ownerId: Long,
    val name: String,
    val value: Float,
)

data class UserWithItemDb(
    @Embedded val user: SplitBillUserDb,
    @Relation(
        parentColumn = "userId",
        entityColumn = "ownerId",
    )
    val items: List<SplitBillItemDb>,
)

data class ItemWithUserDb(
    @Embedded val item: SplitBillItemDb,
    @Relation(
        parentColumn = "ownerId",
        entityColumn = "userId",
    )
    val user: SplitBillUserDb,
)