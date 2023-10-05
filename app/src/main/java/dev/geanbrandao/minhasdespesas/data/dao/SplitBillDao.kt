package dev.geanbrandao.minhasdespesas.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import dev.geanbrandao.minhasdespesas.data.entity.ItemWithUserDb
import dev.geanbrandao.minhasdespesas.data.entity.UserWithItemDb

@Dao
interface SplitBillDao {
    @Transaction
    @Query("SELECT * FROM SplitBillUserDb")
    suspend fun getFinalBill(): List<UserWithItemDb>

    @Transaction
    @Query("SELECT * FROM SplitBillItemDb")
    suspend fun getAllItems(): List<ItemWithUserDb>
}