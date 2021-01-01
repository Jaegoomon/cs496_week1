package com.example.cs496_week1.fragments.contact_fragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.example.cs496_week1.R

class AddContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        val name: EditText = findViewById(R.id.name)
        val student_id: EditText = findViewById(R.id.student_id)
        val phone_number: EditText = findViewById(R.id.phone_number)
        val button: Button = findViewById(R.id.certification)

        button.setOnClickListener {
            Log.d("sequence", "AddContactActivity-button")
            if (!name.getText().toString().isEmpty() &&
                !student_id.getText().toString().isEmpty() &&
                !phone_number.getText().toString().isEmpty()
            ) {
                with(Intent(Intent.ACTION_INSERT)) {
                    this.setType(ContactsContract.RawContacts.CONTENT_TYPE)
                    this.putExtra(
                        ContactsContract.Intents.Insert.NAME,
                        name.getText().toString()
                    )
                    this.putExtra(
                        ContactsContract.Intents.Insert.EMAIL,
                        student_id.getText().toString()
                    )
                    this.putExtra(
                        ContactsContract.Intents.Insert.PHONE,
                        phone_number.getText().toString()
                    )
                    startActivityForResult(this, 1)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("sequence", "AddContactActivity-onActivityResult")
        super.onActivityResult(requestCode, resultCode, data)
        finish()
    }
}