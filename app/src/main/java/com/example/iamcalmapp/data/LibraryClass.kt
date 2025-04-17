package com.example.iamcalmapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LibraryClass(
    val title: String,
    val category:List<Category>
): Parcelable
@Parcelize
data class Category(
    val name: String,
    val video: String
): Parcelable

val libraryList=listOf(
    LibraryClass(
        "УТРО", listOf(Category("Доброе утро","morn1"),
            Category("Настрой на день","morn2")
            )
        ),
    LibraryClass(
        "ДЕНЬ", listOf(Category(" Обновление фокуса","day1"),
            Category("Гармония и баланс","day2")
            )
        ),
    LibraryClass(
        "ВЕЧЕР", listOf(Category("Расслабление перед сном","evening1"),
            Category("Хороший вечер","evening2")
            )
        ),
    LibraryClass(
        "НОЧЬ", listOf(Category("Музыка для сна","sleep1"),
            Category("Медитация перед сном","sleep2")
            )
        ),)

