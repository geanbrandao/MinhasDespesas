package dev.geanbrandao.minhasdespesas.di

import android.content.Context
import androidx.room.Room
import dev.geanbrandao.minhasdespesas.data.dao.MyExpensesDao
import dev.geanbrandao.minhasdespesas.data.dao.SplitBillDao
import dev.geanbrandao.minhasdespesas.data.database.BILL_DB_NAME
import dev.geanbrandao.minhasdespesas.data.database.DB_NAME
import dev.geanbrandao.minhasdespesas.data.database.ExpensesDatabase
import dev.geanbrandao.minhasdespesas.data.database.SplitBillDatabase
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single


const val QUALIFIER_EXPENSE_DB = "expensesDb"
const val QUALIFIER_SPLIT_BILL_DB = "splitBillDb"

@Module
@ComponentScan("dev.geanbrandao.minhasdespesas")
class DatabaseModule { // todo talvez mudar o nome desse modulo para refletir que será apenas do 'gradle module' 'app'

    @Single
    @Named(QUALIFIER_EXPENSE_DB)
    fun provideExpensesDb(appContext: Context): ExpensesDatabase {
        return Room
            .databaseBuilder(
                context = appContext,
                klass = ExpensesDatabase::class.java,
                name = DB_NAME,
            )
            .fallbackToDestructiveMigrationFrom(1, 2)
            .addMigrations(
//                ExpensesDatabase.MIGRATION_1_2,
//                ExpensesDatabase.MIGRATION_2_3,
                ExpensesDatabase.MIGRATION_3_4,
            )
            .build()
    }

    @Single
    @Named(QUALIFIER_EXPENSE_DB)
    fun provideExpenseDbDao(@Named(QUALIFIER_EXPENSE_DB) db: ExpensesDatabase): MyExpensesDao {
        return db.dao
    }

    @Single
    @Named(QUALIFIER_SPLIT_BILL_DB)
    fun provideSplitBillDb(appContext: Context): SplitBillDatabase {
        return Room.databaseBuilder(
            appContext,
            SplitBillDatabase::class.java,
            BILL_DB_NAME,
        ).build()
    }

    @Single
    @Named(QUALIFIER_SPLIT_BILL_DB)
    fun provideSplitBillDbDao(@Named(QUALIFIER_SPLIT_BILL_DB) db: SplitBillDatabase): SplitBillDao {
        return db.splitBillDao
    }
}