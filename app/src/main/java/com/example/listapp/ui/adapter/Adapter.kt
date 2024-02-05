package com.example.listapp.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.listapp.presentation.state.ItemState

class ListAdapter(
	private val onItemClick: (itemState: ItemState) -> Unit
) : ListAdapter<ItemState, ItemViewHolder>(PaymentsDiffCallback()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		ItemViewHolder.from(parent)

	override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
		holder.bind(getItem(position), onItemClick)
	}
}

class PaymentsDiffCallback : DiffUtil.ItemCallback<ItemState>() {

	override fun areItemsTheSame(oldItem: ItemState, newItem: ItemState): Boolean {
		return if (oldItem is ItemState.ContentState && newItem is ItemState.ContentState) {
			oldItem.item.id == newItem.item.id
		} else if (oldItem is ItemState.ForDeleteContentState && newItem is ItemState.ForDeleteContentState) {
			oldItem.isChecked == newItem.isChecked
		} else {
			true
		}
	}

	override fun areContentsTheSame(oldItem: ItemState, newItem: ItemState): Boolean {
		return oldItem == newItem
	}
}