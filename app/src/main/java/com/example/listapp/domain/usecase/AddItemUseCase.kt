package com.example.listapp.domain.usecase

import com.example.listapp.domain.entity.ItemEntity
import com.example.listapp.domain.repository.Repository
import javax.inject.Inject

class AddItemUseCase @Inject constructor(private val repository: Repository) {

	suspend operator fun invoke(itemEntity: ItemEntity) {
		repository.addItem(itemEntity)
	}
}