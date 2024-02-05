package com.example.listapp.domain.usecase

import com.example.listapp.domain.entity.ItemEntity
import com.example.listapp.domain.repository.Repository
import javax.inject.Inject

class GetItemListUseCase @Inject constructor(private val repository: Repository) {

	suspend operator fun invoke(): List<ItemEntity> =
		repository.getListItem()
}