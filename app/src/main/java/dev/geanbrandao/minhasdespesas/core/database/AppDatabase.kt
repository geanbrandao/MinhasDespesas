package dev.geanbrandao.minhasdespesas.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.geanbrandao.minhasdespesas.core.database.AppDatabase.Companion.DB_VERSION
import dev.geanbrandao.minhasdespesas.core.database.converters.Converters
import dev.geanbrandao.minhasdespesas.core.database.db.*

@Database(entities = [
        ExpenseDb::class,
        CategoryDb::class,
        ExpenseCategoryCrossRefDb::class
    ],
    version = DB_VERSION, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract val databaseDao: DatabaseDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "MyExpenses.db"
    }
}