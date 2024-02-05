package com.example.listapp.domain.repository

import com.example.listapp.domain.entity.ItemEntity

interface Repository {

	suspend fun getListItem(): List<ItemEntity>

	suspend fun updateItem(itemEntityForUpdate: ItemEntity)

	suspend fun deleteItems(listItemForDelete: List<ItemEntity>)

	suspend fun addItem(itemEntityForAdd: ItemEntity)
}