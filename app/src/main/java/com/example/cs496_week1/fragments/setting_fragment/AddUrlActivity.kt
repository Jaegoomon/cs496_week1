package com.example.cs496_week1.fragments.setting_fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.cs496_week1.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.FileOutputStream
import java.io.IOException


class AddUrlActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_url)

        val title: EditText = findViewById(R.id.title)
        val content: EditText = findViewById(R.id.content)
        val url: EditText = findViewById(R.id.url)
        val button: Button = findViewById(R.id.certification)

        button.setOnClickListener {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        finish()
    }
}