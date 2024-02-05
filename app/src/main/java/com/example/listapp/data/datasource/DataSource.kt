package com.example.listapp.data.datasource

import com.example.listapp.domain.entity.ItemEntity
import javax.inject.Inject

class DataSourceImpl @Inject constructor() : DataSource {

	override suspend fun getListItem(): List<ItemEntity> =
		listItem

	override suspend fun updateItem(itemEntityForUpdate: ItemEntity) {
		val index = listItem.indexOfFirst { it.id == itemEntityForUpdate.id }
		listItem[index] = itemEntityForUpdate
	}

	override suspend fun deleteItems(listItemForDelete: List<ItemEntity>) {
		listItem.removeAll(listItemForDelete)
	}

	override suspend fun addItem(itemEntityForAdd: ItemEntity) {
		listItem.add(index = 0, itemEntityForAdd.copy(id = listItem.size))
	}

	private val listItem: MutableList<ItemEntity> = mutableListOf()

	private fun initData() {
		for (i in 0..100) {
			listItem.add(
				i, ItemEntity(
					id = i,
					name = names.random(),
					secondName = secondNames.random(),
					numberOfPhone = createNumberOfPhone()
				)
			)
		}
	}

	private fun createNumberOfPhone() = buildString {
		setLength(0)
		append("+")
		for (n in 0..9) {
			append((0..9).random().toString())
		}
	}

	private val names: List<String> = listOf(
		"Август",
		"Авраам",
		"Адам",
		"Адриан",
		"Айдар",
		"Аким",
		"Алан",
		"Алихан",
		"Альберт",
		"Антип",
		"Амур",
		"Амир",
		"Аристарх",
		"Арай",
		"Арман",
		"Арсен",
		"Арсений",
		"Артур",
		"Архип",
		"Аскольд",
		"Богдан",
		"Борислав",
		"Бруно",
		"Булат",
		"Вальтер",
		"Вахтанг",
		"Вениамин",
		"Венцеслав",
		"Винсент",
		"Вилльям",
		"Влас",
		"Всеволод",
		"Гавриил",
		"Генри",
		"Гарри",
		"Геракл",
		"Герман",
		"Глеб",
		"Гордей"
	)

	private val secondNames: List<String> = listOf(
		"Авдеев",
		"Агапов",
		"Агафонов",
		"Агеев",
		"Акимов",
		"Аксенов",
		"Александров",
		"Алексеев",
		"Алехин",
		"Алешин",
		"Ананьев",
		"Андреев",
		"Андрианов",
		"Аникин",
		"Анисимов",
		"Анохин",
		"Антипов",
		"Антонов",
		"Артамонов",
		"Артемов",
		"Артемьев",
		"Архипов",
		"Астафьев",
		"Астахов",
		"Афанасьев",
		"Бабушкин",
		"Баженов",
		"Балашов",
		"Баранов",
		"Барсуков",
		"Басов",
		"Безруков",
		"Беликов",
		"Белкин",
		"Белов",
		"Белозёров",
		"Белоусов",
		"Беляев",
		"Беляков",
		"Березин",
		"Беспалов",
		"Бессонов",
		"Бирюков",
		"Блинов",
		"Блохин",
		"Бобров",
		"Бобылёв",
		"Богданов",
		"Богомолов",
		"Болдырев",
		"Большаков",
		"Бондарев",
		"Борисов",
		"Бородин",
		"Бочаров",
		"Брагин",
		"Булатов",
		"Булгаков",
		"Буров",
		"Быков",
		"Бычков",
		"Вавилов",
		"Васильев",
		"Вдовин",
		"Верещагин",
		"Веселов",
		"Вешняков",
		"Виноградов",
		"Винокуров",
		"Вишневский",
		"Вишняков",
		"Владимиров",
		"Власов",
		"Волков",
		"Волошин",
		"Воробьев",
		"Воронин",
		"Воронков",
		"Воронов",
		"Воронцов",
		"Высоцкий",
		"Гаврилов",
		"Галкин",
		"Герасимов",
		"Гладков",
		"Глебов",
		"Глухов",
		"Глушков",
		"Голиков",
		"Голованов",
		"Головин",
		"Голубев",
		"Гончаров",
		"Горбачев",
		"Горбунов",
		"Гордеев",
		"Горелов",
		"Горлов",
		"Горохов",
		"Горшков",
		"Горюнов",
		"Горячев",
		"Грачев",
		"Греков",
		"Грибов",
		"Григорьев",
		"Гришин",
		"Громов",
	)

	init {
		initData()
	}
}

interface DataSource {

	suspend fun getListItem(): List<ItemEntity>

	suspend fun updateItem(itemEntityForUpdate: ItemEntity)

	suspend fun deleteItems(listItemForDelete: List<ItemEntity>)

	suspend fun addItem(itemEntityForAdd: ItemEntity)
}