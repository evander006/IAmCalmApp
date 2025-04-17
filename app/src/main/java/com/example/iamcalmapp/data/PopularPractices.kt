package com.example.iamcalmapp.data


data class PopularPractices(
    val title: String,
    val desc: String,
    val videoUrl: String
)
val videoCards = listOf(
    PopularPractices(
        title = "Гармония сна",
        desc = "Погрузитесь в атмосферу спокойствия и тишины",
        videoUrl = "sleep"
    ),
    PopularPractices(
        title = "Приветствие дня",
        desc = "Приветствуйте каждое утро с благодарностью",
        videoUrl = "hello"
    ),
    PopularPractices(
        title = "Утро с книгой",
        desc = "Погрузитесь в чтение положительной литературы",
        videoUrl = "morn"
    )
)
