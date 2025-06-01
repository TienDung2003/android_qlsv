package com.example.quan_ly_sv

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val edtName = findViewById<EditText>(R.id.edtName)
        val edtMssv = findViewById<EditText>(R.id.edtMssv)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPhone = findViewById<EditText>(R.id.edtPhone)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val name = edtName.text.toString().trim()
            val mssv = edtMssv.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val phone = edtPhone.text.toString().trim()

            if (name.isEmpty() || mssv.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Nếu hợp lệ, tạo Student object và trả kết quả về MainActivity
            val student = Student(0, name, mssv, email, phone)
            val intent = Intent().apply {
                putExtra("student", student)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
