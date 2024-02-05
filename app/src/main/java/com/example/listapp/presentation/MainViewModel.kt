package com.example.listapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listapp.domain.entity.ItemEntity
import com.example.listapp.domain.usecase.AddItemUseCase
import com.example.listapp.domain.usecase.DeleteItemUseCase
import com.example.listapp.domain.usecase.GetItemListUseCase
import com.example.listapp.domain.usecase.UpdateItemUseCase
import com.example.listapp.presentation.state.ItemState
import com.example.listapp.presentation.state.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val getItemListUseCase: GetItemListUseCase,
	private val deleteItemUseCase: DeleteItemUseCase,
	private val updateItemUseCase: UpdateItemUseCase,
	private val addItemUseCase: AddItemUseCase
) : ViewModel() {

	private val _listMut = MutableLiveData<State>(State.Initial)
	val listLive: LiveData<State> = _listMut

	private val listForDelete: MutableSet<ItemEntity> = mutableSetOf()

	init {
		getContactList()
	}

	fun getContactList() {
		viewModelScope.launch {
			val list = getItemListUseCase()
			_listMut.value = State.Content(listState = toItemState(list))
		}
	}

	private fun toItemState(listItem: List<ItemEntity>): List<ItemState> {
		val listState: MutableList<ItemState> = mutableListOf()
		listItem.forEach {
			listState.add(ItemState.ContentState(toEdit = false, item = it))
		}
		return listState
	}

	private fun toDeleteItemState(listItem: List<ItemEntity>): List<ItemState> {
		val listState: MutableList<ItemState> = mutableListOf()
		listItem.forEach {
			listState.add(ItemState.ForDeleteContentState(isChecked = false, item = it))
		}
		return listState
	}

	fun clickToItem(itemState: ItemState) {
		when (itemState) {
			is ItemState.ContentState          -> editItem(itemState)
			is ItemState.ForDeleteContentState -> deleteItem(itemState)
		}
	}

	private fun deleteItem(itemState: ItemState.ForDeleteContentState) {
		if (itemState.isChecked) {
			listForDelete.add(itemState.item)
		} else {
			listForDelete.remove(itemState.item)
		}
	}

	fun replaceStateToDelete() {
		viewModelScope.launch {
			val list = getItemListUseCase()
			_listMut.value = State.ContentForDelete(listState = toDeleteItemState(list))
		}
	}

	fun delete() {
		viewModelScope.launch {
			deleteItemUseCase(listForDelete.toList())
			getContactList()
		}
	}

	fun addItem(itemEntity: ItemEntity?) {
		if (itemEntity != null) {
			viewModelScope.launch {
				addItemUseCase(itemEntity)
				getContactList()
			}
		} else {
			_listMut.value = State.ContentForAddItem
		}
	}

	fun editItem(itemState: ItemState.ContentState) {
		if (itemState.toEdit) {
			viewModelScope.launch {
				updateItemUseCase(itemState.item)
				getContactList()
			}
		} else {
			_listMut.value = State.ContentForEditItem(itemState.item)
		}
	}
}