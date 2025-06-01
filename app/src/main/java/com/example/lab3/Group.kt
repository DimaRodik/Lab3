package com.example.lab3

class Group(vararg students: Student) {
    private val members = students.toList()

    // Звернення до студента за індексом
    operator fun get(index: Int): Student {
        return members[index]
    }

    // Повертає студента з найвищим середнім балом
    fun getTopStudent(): Student? {
        return members.maxByOrNull { it.getAverage() }
    }
}
