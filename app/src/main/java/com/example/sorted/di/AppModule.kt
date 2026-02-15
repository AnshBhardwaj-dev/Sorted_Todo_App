package com.example.sorted.di

import android.content.Context
import androidx.room.Room
import com.example.sorted.data.local.Database.TodoDatabase
import com.example.sorted.data.local.dao.TodoDao
import com.example.sorted.data.repository.TodoRepositoryImpl
import com.example.sorted.domain.repository.TodoRepository
import com.example.sorted.domain.usecase.AddTodoUseCase
import com.example.sorted.domain.usecase.DeleteTodoUseCase
import com.example.sorted.domain.usecase.GetAllTodoUseCase
import com.example.sorted.domain.usecase.TodoUseCases
import com.example.sorted.domain.usecase.UpdateTodoUseCase
import com.example.sorted.domain.validation.TodoValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // ================= DATABASE =================

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todo_database"
        ).build()
    }

    // ================= DAO =================

    @Provides
    @Singleton
    fun provideTodoDao(
        database: TodoDatabase
    ): TodoDao {
        return database.todoDao()
    }

    // ================= REPOSITORY =================

    @Provides
    @Singleton
    fun provideRepository(
        dao: TodoDao
    ): TodoRepository {
        return TodoRepositoryImpl(dao)
    }

    // ================= VALIDATOR =================

    @Provides
    @Singleton
    fun provideValidator(): TodoValidator {
        return TodoValidator()
    }

    // ================= USE CASES =================

    @Provides
    @Singleton
    fun provideTodoUseCases(
        repository: TodoRepository,
        validator: TodoValidator
    ): TodoUseCases {

        return TodoUseCases(
            addTodo = AddTodoUseCase(
                repository,
                validator
            ),
            updateTodo = UpdateTodoUseCase(
                repository,
                validator
            ),
            deleteTodo = DeleteTodoUseCase(repository),
            getAllTodo = GetAllTodoUseCase(repository)
        )
    }
}