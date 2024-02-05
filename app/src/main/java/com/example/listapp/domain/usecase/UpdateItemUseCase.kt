package com.example.listapp.domain.usecase

import com.example.listapp.domain.entity.ItemEntity
import com.example.listapp.domain.repository.Repository
import javax.inject.Inject

class UpdateItemUseCase @Inject constructor(private val repository: Repository) {

	suspend operator fun invoke(item: ItemEntity) {
		repository.updateItem(item)
	}
}