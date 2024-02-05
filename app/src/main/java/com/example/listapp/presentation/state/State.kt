package com.example.listapp.presentation.state

import com.example.listapp.domain.entity.ItemEntity

sealed class State {

	data object Initial : State()

	data class Content(val listState: List<ItemState>) : State()

	data class ContentForDelete(val listState: List<ItemState>) : State()

	data object ContentForAddItem : State()

	data class ContentForEditItem(val item: ItemEntity) : State()
}