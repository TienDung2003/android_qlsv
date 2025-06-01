package com.example.quan_ly_sv

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class UpdateStudentActivity : AppCompatActivity() {
    private lateinit var student: Student
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_student)

        val edtName = findViewById<EditText>(R.id.edtName)
        val edtMssv = findViewById<EditText>(R.id.edtMssv)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPhone = findViewById<EditText>(R.id.edtPhone)
        val btnUpdate = findViewById<Button>(R.id.btnSave)

        // Nhận dữ liệu Student và position
        student = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("student", Student::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("student")!!
        }
        position = intent.getIntExtra("position", -1)

        // Đặt dữ liệu cũ vào EditText
        edtName.setText(student.name)
        edtMssv.setText(student.mssv)
        edtEmail.setText(student.email)
        edtPhone.setText(student.phone)

        // Sự kiện cập nhật
        btnUpdate.setOnClickListener {
            val name = edtName.text.toString().trim()
            val mssv = edtMssv.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val phone = edtPhone.text.toString().trim()

            if (name.isEmpty() || mssv.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Trả kết quả đã cập nhật
            val updatedStudent = Student(student.id, name, mssv, email, phone)
            val resultIntent = Intent().apply {
                putExtra("student", updatedStudent)
                putExtra("position", position)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
