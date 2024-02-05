package com.example.listapp.presentation.state

import com.example.listapp.domain.entity.ItemEntity

sealed class ItemState {

	data class ContentState(val toEdit: Boolean, val item: ItemEntity) : ItemState()

	data class ForDeleteContentState(val isChecked: Boolean, val item: ItemEntity) : ItemState()
}