package dev.geanbrandao.minhasdespesas.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.geanbrandao.minhasdespesas.data.converters.MyExpensesConverters
import dev.geanbrandao.minhasdespesas.data.dao.MyExpensesDao
import dev.geanbrandao.minhasdespesas.data.entity.CategoryEntity
import dev.geanbrandao.minhasdespesas.data.entity.ExpenseCategoryCrossRefEntity
import dev.geanbrandao.minhasdespesas.data.entity.ExpenseEntity

const val DB_VERSION = 5
const val DB_NAME = "MyExpenses.db"

@Database(
    entities = [
        ExpenseEntity::class,
        CategoryEntity::class,
        ExpenseCategoryCrossRefEntity::class
    ],
    version = DB_VERSION,
    exportSchema = true,
    autoMigrations = [
//        AutoMigration(1, 2),
//        AutoMigration(2, 3),
        AutoMigration(4, 5)
    ]
)
@TypeConverters(MyExpensesConverters::class)
abstract class ExpensesDatabase : RoomDatabase() {
    abstract val dao: MyExpensesDao

    companion object {
//        val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//
//            }
//        }
//        val MIGRATION_2_3 = object : Migration(2, 3) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//
//            }
//        }
        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Crie a nova tabela CategoryEntity
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS CategoryEntity (" +
                            "categoryId INTEGER PRIMARY KEY NOT NULL, " +
                            "name TEXT NOT NULL, " +
                            "icon TEXT NOT NULL, " +
                            "canRemove INTEGER NOT NULL DEFAULT 0)"
                )

                // Copie os dados da tabela CategoryData para a nova tabela CategoryEntity
                database.execSQL(
                    "INSERT INTO CategoryEntity (categoryId, name, icon, canRemove) " +
                            "SELECT categoryId, name, icon, CASE WHEN canRemove = 1 THEN 1 ELSE 0 END FROM CategoryData"
                )

                // Exclua a tabela CategoryData antiga
                database.execSQL("DROP TABLE IF EXISTS CategoryData")

                // Crie a nova tabela ExpenseEntity
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS ExpenseEntity (" +
                            "expenseId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "amount REAL NOT NULL, " +
                            "name TEXT NOT NULL, " +
                            "selectedDate TEXT NOT NULL, " +
                            "description TEXT NOT NULL, " +
                            "createdAt TEXT NOT NULL, " +
                            "updatedAt TEXT NOT NULL)"
                )

                // Copie os dados da tabela ExpenseData para a nova tabela ExpenseEntity
                database.execSQL(
                    "INSERT INTO ExpenseEntity (amount, name, selectedDate, description, createdAt, updatedAt) " +
                            "SELECT amount, name, selectedDate, description, createdAt, updatedAt FROM ExpenseData"
                )

                // Exclua a tabela ExpenseData antiga
                database.execSQL("DROP TABLE IF EXISTS ExpenseData")

                // Crie a nova tabela ExpenseCategoryCrossRefEntity
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS ExpenseCategoryCrossRefEntity (" +
                            "expenseId INTEGER NOT NULL, " +
                            "categoryId INTEGER NOT NULL, " +
                            "PRIMARY KEY(expenseId, categoryId))"
                )

                // Copie os dados da tabela ExpenseCategory para a nova tabela ExpenseCategoryCrossRefEntity
                database.execSQL(
                    "INSERT INTO ExpenseCategoryCrossRefEntity (expenseId, categoryId) " +
                            "SELECT expenseId, categoryId FROM ExpenseCategory"
                )

                // Exclua a tabela ExpenseCategory antiga
                database.execSQL("DROP TABLE IF EXISTS ExpenseCategory")
            }
        }
    }
}