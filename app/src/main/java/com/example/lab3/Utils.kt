package com.example.lab3

import kotlinx.coroutines.delay

suspend fun fetchGradesFromServer(): List<Int> {
    delay(2000)
    return listOf(90, 85, 78, 92, 88)
}
