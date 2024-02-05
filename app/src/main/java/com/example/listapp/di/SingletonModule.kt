package com.example.listapp.di

import com.example.listapp.data.datasource.DataSource
import com.example.listapp.data.datasource.DataSourceImpl
import com.example.listapp.data.repository.RepositoryImpl
import com.example.listapp.domain.repository.Repository
import com.example.listapp.domain.usecase.AddItemUseCase
import com.example.listapp.domain.usecase.DeleteItemUseCase
import com.example.listapp.domain.usecase.GetItemListUseCase
import com.example.listapp.domain.usecase.UpdateItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

	@Provides
	@Singleton
	fun provideDataSource(): DataSource {
		return DataSourceImpl()
	}

	@Provides
	@Singleton
	fun provideRepository(dataSource: DataSource): Repository {
		return RepositoryImpl(dataSource)
	}

	@Provides
	@Singleton
	fun provideGetItemListUseCase(repository: Repository): GetItemListUseCase {
		return GetItemListUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideDeleteItemUseCase(repository: Repository): DeleteItemUseCase {
		return DeleteItemUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideUpdateItemUseCase(repository: Repository): UpdateItemUseCase {
		return UpdateItemUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideAddItemUseCase(repository: Repository): AddItemUseCase {
		return AddItemUseCase(repository)
	}
}