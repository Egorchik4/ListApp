package com.example.listapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listapp.databinding.ItemListBinding
import com.example.listapp.presentation.state.ItemState

class ItemViewHolder(
	private val binding: ItemListBinding
) : RecyclerView.ViewHolder(binding.root) {

	companion object {

		fun from(parent: ViewGroup): ItemViewHolder {
			val inflater = LayoutInflater.from(parent.context)
			val binding = ItemListBinding.inflate(inflater, parent, false)
			return ItemViewHolder(binding)
		}
	}

	fun bind(
		itemState: ItemState,
		onItemClick: (itemState: ItemState) -> Unit
	) {
		with(binding) {
			when (itemState) {
				is ItemState.ContentState          -> {
					textName.text = itemState.item.name
					textSecondName.text = itemState.item.secondName
					textNumberOfPhone.text = itemState.item.numberOfPhone
					checkBox.visibility = View.INVISIBLE
					root.setOnClickListener { onItemClick(itemState) }
				}

				is ItemState.ForDeleteContentState -> {
					textName.text = itemState.item.name
					textSecondName.text = itemState.item.secondName
					textNumberOfPhone.text = itemState.item.numberOfPhone
					checkBox.visibility = View.VISIBLE
					checkBox.setOnClickListener {
						onItemClick(itemState.copy(isChecked = checkBox.isChecked))
					}
				}
			}
		}
	}
}