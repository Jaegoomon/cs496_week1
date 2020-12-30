package com.example.cs496_week1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener

class AddContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        val name: EditText = findViewById(R.id.name)
        val student_id: EditText = findViewById(R.id.student_id)
        val phone_number: EditText = findViewById(R.id.phone_number)
        val button: Button = findViewById(R.id.certification)

        button.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra("name", name.text.toString())
                putExtra("student_id", student_id.text.toString())
                putExtra("phone_number", phone_number.text.toString())
            })
            finish()
        }
    }
}