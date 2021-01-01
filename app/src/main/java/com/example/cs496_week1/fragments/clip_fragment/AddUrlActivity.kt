package com.example.cs496_week1.fragments.clip_fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.cs496_week1.R
import kotlinx.android.synthetic.main.activity_add_url.view.*


class AddUrlActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_url)


        val button: Button = findViewById(R.id.certification)

        button.setOnClickListener {
            val url: EditText = findViewById(R.id.url)
            val title: EditText = findViewById(R.id.title)
            val content: EditText = findViewById(R.id.content)

            val intent = Intent()
            intent.putExtra("url", url.text)
            //intent.putExtra("title", title.text)
            //intent.putExtra("content", content.text)
            Log.d("asdf", "" + url.text)
            setResult(1234, intent)
            finish()
        }
    }
}