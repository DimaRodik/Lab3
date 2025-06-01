package com.example.lab3

class Student(name: String, age: Int = 0, grades: List<Int> = listOf()) {

    private var _name: String = name.trim().replaceFirstChar { it.uppercase() }
    private var _age: Int = if (age >= 0) age else 0
    private var _grades: List<Int> = grades

    var name: String
        get() = _name
        set(value) {
            _name = value.trim().replaceFirstChar { it.uppercase() }
        }

    var age: Int
        get() = _age
        set(value) {
            if (value >= 0) _age = value
        }

    val isAdult: Boolean
        get() = _age >= 18

    val status: String by lazy {
        if (isAdult) "Adult" else "Minor"
    }

    init {
        println("Student object created: $_name")
    }

    constructor(name: String) : this(name, 0, listOf())

    fun getAverage(): Double {
        return if (_grades.isNotEmpty()) _grades.average() else 0.0
    }

    fun processGrades(operation: (Int) -> Int) {
        _grades = _grades.map(operation)
    }

    fun updateGrades(grades: List<Int>) {
        _grades = grades
    }

    operator fun plus(other: Student): Student {
        return Student(this.name, this.age, this._grades + other._grades)
    }

    operator fun times(multiplier: Int): Student {
        return Student(this.name, this.age, this._grades.map { it * multiplier })
    }

    override operator fun equals(other: Any?): Boolean {
        return other is Student && this.name == other.name && this.getAverage() == other.getAverage()
    }

    override fun toString(): String {
        return "Name: $name, Age: $age, Grades: $_grades, Avg: ${getAverage()}"
    }
}
