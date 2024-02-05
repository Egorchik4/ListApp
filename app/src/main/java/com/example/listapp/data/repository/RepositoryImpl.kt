package com.example.listapp.data.repository

import com.example.listapp.data.datasource.DataSource
import com.example.listapp.domain.entity.ItemEntity
import com.example.listapp.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val dataSource: DataSource) : Repository {

	override suspend fun getListItem(): List<ItemEntity> =
		dataSource.getListItem()

	override suspend fun updateItem(itemEntityForUpdate: ItemEntity) {
		dataSource.updateItem(itemEntityForUpdate)
	}

	override suspend fun deleteItems(listItemForDelete: List<ItemEntity>) {
		dataSource.deleteItems(listItemForDelete)
	}

	override suspend fun addItem(itemEntityForAdd: ItemEntity) {
		dataSource.addItem(itemEntityForAdd)
	}
}