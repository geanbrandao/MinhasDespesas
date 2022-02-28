package dev.geanbrandao.minhasdespesas.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.geanbrandao.minhasdespesas.core.database.AppDatabase
import dev.geanbrandao.minhasdespesas.feature.data.repository.ExpenseRepositoryImpl
import dev.geanbrandao.minhasdespesas.feature.domain.repository.ExpenseRepository
import dev.geanbrandao.minhasdespesas.feature.domain.use_case.ExpenseUseCases
import dev.geanbrandao.minhasdespesas.feature.domain.use_case.GetCategories
import dev.geanbrandao.minhasdespesas.feature.domain.use_case.GetExpenses
import dev.geanbrandao.minhasdespesas.feature.domain.use_case.PostCategories
import dev.geanbrandao.minhasdespesas.feature.domain.use_case.PostExpense
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideExpenseRepository(appDatabase: AppDatabase): ExpenseRepository {
        return ExpenseRepositoryImpl(appDatabase.databaseDao)
    }

    @Provides
    @Singleton
    fun provideExpenseUseCases(repository: ExpenseRepository): ExpenseUseCases {
        return ExpenseUseCases(
            getExpenses = GetExpenses(repository = repository),
            postCategories = PostCategories(repository = repository),
            getCategories = GetCategories(repository = repository),
            postExpense = PostExpense(repository = repository)
        )
    }
}