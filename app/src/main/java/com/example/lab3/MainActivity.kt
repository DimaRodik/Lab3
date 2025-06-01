package com.example.lab3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var students: List<Student>
    private lateinit var student: Student
    private lateinit var student2: Student
    private lateinit var student3: Student
    private lateinit var student4: Student


    private lateinit var textStudentInfo: TextView
    private lateinit var btnFetchGrades: Button
    private lateinit var btnProcessGrades: Button
    private lateinit var btnMultiplyGrades: Button
    private lateinit var btnCreateGroup: Button
    private lateinit var btnShowAll: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        setContentView(R.layout.activity_main)

        // View binding
        textStudentInfo = findViewById(R.id.textStudentInfo)
        btnFetchGrades = findViewById(R.id.btnFetchGrades)
        btnProcessGrades = findViewById(R.id.btnProcessGrades)
        btnMultiplyGrades = findViewById(R.id.btnMultiplyGrades)
        btnCreateGroup = findViewById(R.id.btnCreateGroup)
        btnShowAll = findViewById(R.id.btnShowAll)

        btnShowAll.setOnClickListener {
            val allStudentsInfo = students.joinToString("\n\n") { it.toString() + "\nСтатус: " + it.status }
            updateUI("Усі студенти:\n\n$allStudentsInfo")
        }

        // Створення студентів
        student = Student("  ivan ")
        student.age = 19

        student2 = Student("maria", 18, listOf(95, 91, 93))
        student3 = Student("oleg", 17, listOf(70, 60, 65))
        student4 = Student("katya", 20, listOf(88, 92, 85))

        students = listOf(student, student2, student3, student4)

        updateUI("Студент створений:\n$student\nСтатус: ${student.status}")

        // 1. Завантажити оцінки
        btnFetchGrades.setOnClickListener {
            launch {
                updateUI("Завантаження оцінок...")

                students.forEach { student ->
                    val grades = fetchGradesFromServer()
                    student.updateGrades(grades)
                }

                val info = students.joinToString("\n\n") { it.toString() + "\nСтатус: " + it.status }
                updateUI("Оцінки оновлені:\n\n$info")
            }
        }


        // 2. Збільшити всі оцінки на 5
        btnProcessGrades.setOnClickListener {
            students.forEach { it.processGrades { grade -> grade + 5 } }

            val info = students.joinToString("\n\n") { it.toString() + "\nСтатус: " + it.status }
            updateUI("Оцінки +5:\n\n$info")
        }

        // 3. Помножити всі оцінки на 2 (оператор *)
        btnMultiplyGrades.setOnClickListener {
            students = students.map { it * 2 }

            val info = students.joinToString("\n\n") { it.toString() + "\nСтатус: " + it.status }
            updateUI("Оцінки ×2:\n\n$info")
        }

        // 4. Створити групу й показати топ-студента
        btnCreateGroup.setOnClickListener {
            val group = Group(student, student2, student3, student4)
            val top = group.getTopStudent()
            updateUI("Група з 4 студентів створена.\n\nТоп-студент:\n$top")
        }
    }

    private fun updateUI(text: String) {
        textStudentInfo.text = text
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
