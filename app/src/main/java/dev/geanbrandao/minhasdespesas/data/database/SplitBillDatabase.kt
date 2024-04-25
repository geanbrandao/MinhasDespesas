package dev.geanbrandao.minhasdespesas.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.geanbrandao.minhasdespesas.data.dao.SplitBillDao
import dev.geanbrandao.minhasdespesas.data.entity.SplitBillItemDb
import dev.geanbrandao.minhasdespesas.data.entity.SplitBillUserDb

const val BILL_DB_VERSION = 3
const val BILL_DB_NAME = "MyExpenses.SplitBill.db"
@Database(
    entities = [SplitBillUserDb::class, SplitBillItemDb::class],
    version = BILL_DB_VERSION, exportSchema = false,
)
abstract class SplitBillDatabase: RoomDatabase() {
    abstract val splitBillDao: SplitBillDao
}