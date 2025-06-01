package com.example.quan_ly_sv

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var db: StudentDatabaseHelper
    private lateinit var students: MutableList<Student>
    private lateinit var adapter: StudentAdapter

    // Launcher cho cập nhật sinh viên
    private val updateStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val student = data?.getParcelableExtra<Student>("student")
            val position = data?.getIntExtra("position", -1) ?: -1
            if (student != null && position != -1) {
                db.updateStudent(student)
                students.clear()
                students.addAll(db.getAllStudents())
                adapter.notifyDataSetChanged()
            }
        }
    }


    // Launcher cho thêm sinh viên mới
    private val addStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val student = data?.getParcelableExtra<Student>("student")
            if (student != null) {
                db.insertStudent(student)
                students.clear()
                students.addAll(db.getAllStudents())
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = StudentDatabaseHelper(this)
        students = db.getAllStudents()

        val lv = findViewById<ListView>(R.id.lvStudents)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        adapter = StudentAdapter(this, students) { action, student, position ->
            when (action) {
                "update" -> {
                    val intent = Intent(this, UpdateStudentActivity::class.java)
                    intent.putExtra("student", student)
                    intent.putExtra("position", position)
                    updateStudentLauncher.launch(intent)
                }
                "delete" -> {
                    AlertDialog.Builder(this)
                        .setTitle("Xác nhận")
                        .setMessage("Xóa sinh viên này?")
                        .setPositiveButton("Có") { _, _ ->
                            db.deleteStudent(student.id)
                            students.removeAt(position)
                            adapter.notifyDataSetChanged()
                        }
                        .setNegativeButton("Không", null)
                        .show()
                }
                "call" -> startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${student.phone}")))
                "email" -> startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${student.email}")))
            }
        }

        lv.adapter = adapter

        btnAdd.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            addStudentLauncher.launch(intent)
        }
    }
}
