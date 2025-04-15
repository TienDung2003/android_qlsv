package com.example.quan_ly_sv

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DeleteStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_student)

        val edtIdToDelete = findViewById<EditText>(R.id.etIdToDelete)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        btnDelete.setOnClickListener {
            val id = edtIdToDelete.text.toString().trim()
            if (id.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("id", id)
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Vui lòng nhập MSSV để xóa", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


