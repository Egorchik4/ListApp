package com.example.listapp.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.listapp.databinding.ActivityMainBinding
import com.example.listapp.databinding.ItemCustomDialogBinding
import com.example.listapp.domain.entity.ItemEntity
import com.example.listapp.presentation.MainViewModel
import com.example.listapp.presentation.state.ItemState
import com.example.listapp.presentation.state.State
import com.example.listapp.ui.adapter.ListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding
	private val viewModel: MainViewModel by viewModels()
	private lateinit var adapter: ListAdapter

	companion object {

		const val ADD_ITEM = "Add"
		const val EDIT_ITEM = "Edit"
		const val VALUE_IS_EMPTY = "Value is empty"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		setAdapter()
		setObserver()
		setListeners()
	}

	private fun setAdapter() {
		adapter = ListAdapter(viewModel::clickToItem)
		binding.rcView.adapter = adapter
	}

	private fun setObserver() {
		viewModel.listLive.observe(this) {
			when (it) {
				is State.Initial            -> {}

				is State.Content            -> {
					handleContentState(it)
				}

				is State.ContentForDelete   -> {
					handleContentForDeleteState(it)
				}

				is State.ContentForAddItem  -> {
					handleContentForAddItemState()
				}

				is State.ContentForEditItem -> {
					handleContentForEditItemState(it)
				}
			}
		}
	}

	private fun handleContentState(state: State.Content) {
		with(binding) {
			buttonCancel.visibility = View.GONE
			buttonDelete.visibility = View.GONE
			buttonAddItem.visibility = View.VISIBLE
			deleteToggleButton.isChecked = false
		}
		adapter.submitList(state.listState)
	}

	private fun handleContentForDeleteState(state: State.ContentForDelete) {
		with(binding) {
			buttonCancel.visibility = View.VISIBLE
			buttonDelete.visibility = View.VISIBLE
			buttonAddItem.visibility = View.GONE
			deleteToggleButton.isChecked = true
		}
		adapter.submitList(state.listState)
	}

	private fun handleContentForAddItemState() {
		customAlertDialog(itemEntity = null) {
			viewModel.addItem(it)
		}
	}

	private fun handleContentForEditItemState(state: State.ContentForEditItem) {
		customAlertDialog(itemEntity = state.item) {
			viewModel.editItem(ItemState.ContentState(toEdit = true, item = it))
		}
	}

	private fun setListeners() {
		with(binding) {
			deleteToggleButton.setOnClickListener {
				if (deleteToggleButton.isChecked) {
					viewModel.replaceStateToDelete()
				} else {
					viewModel.getContactList()
				}
			}
			buttonAddItem.setOnClickListener {
				viewModel.addItem(null)
			}
			buttonDelete.setOnClickListener {
				viewModel.delete()
			}
			buttonCancel.setOnClickListener {
				viewModel.getContactList()
			}
		}
	}

	private fun customAlertDialog(itemEntity: ItemEntity?, onClick: (ItemEntity) -> Unit) {
		if (itemEntity != null) {
			val dialogBinding = ItemCustomDialogBinding.inflate(layoutInflater)
			val dialog = AlertDialog.Builder(this)
				.setTitle(EDIT_ITEM)
				.setView(dialogBinding.root)
				.setPositiveButton(EDIT_ITEM, null)
				.create()

			dialog.setOnShowListener {
				dialogBinding.editTextName.requestFocus()
				dialogBinding.editTextName.setText(itemEntity.name)
				dialogBinding.editTextSecondName.setText(itemEntity.secondName)
				dialogBinding.editTextNumberOfPhone.setText(itemEntity.numberOfPhone)

				dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
					val name: String = dialogBinding.editTextName.text.toString()
					val secondName: String = dialogBinding.editTextSecondName.text.toString()
					val numberOfPhone: String = dialogBinding.editTextNumberOfPhone.text.toString()
					if (name.isBlank()) {
						dialogBinding.editTextName.error = VALUE_IS_EMPTY
						return@setOnClickListener
					} else if (secondName.isBlank()) {
						dialogBinding.editTextSecondName.error = VALUE_IS_EMPTY
						return@setOnClickListener
					} else if (numberOfPhone.isEmpty() && numberOfPhone.length != 10) {
						dialogBinding.editTextNumberOfPhone.error = VALUE_IS_EMPTY
						return@setOnClickListener
					}
					onClick.invoke(
						ItemEntity(
							id = itemEntity.id,
							name = name,
							secondName = secondName,
							numberOfPhone = numberOfPhone
						)
					)
					dialog.dismiss()
				}
			}
			dialog.show()
		} else {
			val dialogBinding = ItemCustomDialogBinding.inflate(layoutInflater)
			val dialog = AlertDialog.Builder(this)
				.setTitle(ADD_ITEM)
				.setView(dialogBinding.root)
				.setPositiveButton(ADD_ITEM, null)
				.create()

			dialog.setOnShowListener {
				dialogBinding.editTextName.requestFocus()

				dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
					val name: String = dialogBinding.editTextName.text.toString()
					val secondName: String = dialogBinding.editTextSecondName.text.toString()
					val numberOfPhone: String = dialogBinding.editTextNumberOfPhone.text.toString()
					if (name.isBlank()) {
						dialogBinding.editTextName.error = VALUE_IS_EMPTY
						return@setOnClickListener
					} else if (secondName.isBlank()) {
						dialogBinding.editTextSecondName.error = VALUE_IS_EMPTY
						return@setOnClickListener
					} else if (numberOfPhone.isEmpty()) {
						dialogBinding.editTextNumberOfPhone.error = VALUE_IS_EMPTY
						return@setOnClickListener
					}
					onClick.invoke(
						ItemEntity(
							name = name,
							secondName = secondName,
							numberOfPhone = numberOfPhone
						)
					)
					dialog.dismiss()
				}
			}
			dialog.show()
		}
	}
}