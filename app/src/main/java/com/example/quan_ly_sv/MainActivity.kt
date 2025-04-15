package com.example.quan_ly_sv

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var studentAdapter: StudentAdapter
    private val studentList = mutableListOf<Student>()
    private val addStudentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val name = data?.getStringExtra("name")
                val id = data?.getStringExtra("id")
                // Thêm sinh viên vào danh sách
                if (!name.isNullOrBlank() && !id.isNullOrBlank()) {
                    val student = Student(name, id)
                    studentList.add(0, student) // Thêm vào đầu danh sách
                    studentAdapter.notifyItemInserted(0)
                }
            }
        }

    private val deleteStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val idToDelete = data?.getStringExtra("id")
            if (!idToDelete.isNullOrBlank()) {
                val index = studentList.indexOfFirst { it.id == idToDelete }
                if (index != -1) {
                    studentList.removeAt(index)
                    studentAdapter.notifyItemRemoved(index)
                    Toast.makeText(this, "Đã xóa sinh viên có MSSV $idToDelete", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Không tìm thấy sinh viên với MSSV $idToDelete", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentAdapter = StudentAdapter(studentList)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = studentAdapter

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            addStudentLauncher.launch(intent)        }

        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            val intent = Intent(this, DeleteStudentActivity::class.java)
            deleteStudentLauncher.launch(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                1 -> {
                    val name = data.getStringExtra("name") ?: ""
                    val id = data.getStringExtra("id") ?: ""
                    studentAdapter.addStudent(Student(name, id))
                }
                2 -> {
                    val id = data.getStringExtra("id") ?: ""
                    studentAdapter.removeStudentById(id)
                }
            }
        }
    }
}
