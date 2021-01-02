package com.example.cs496_week1.fragments.clip_fragment

import android.content.Intent
import android.icu.text.CaseMap
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.cs496_week1.R
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add_url.view.*
import java.lang.Exception

class AddUrlActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    private lateinit var button: Button
    private lateinit var url: EditText
    private lateinit var title: EditText
    private lateinit var content: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_url)

        realm = Realm.getDefaultInstance()
        button = findViewById(R.id.certification)
        url = findViewById(R.id.url)
        title = findViewById(R.id.title)
        content = findViewById(R.id.content)

        button.setOnClickListener {
            addClibDB()
            finish()
        }
    }

    fun addClibDB() {
        try {
            val clipData = ClipData()
            val index: Number? = realm.where(ClipData::class.java).max("id")
            val nextId = if (index == null) {
                0
            } else {
                index.toInt() + 1
            }
            clipData.id = nextId
            clipData.url = url.text.toString()
            clipData.title = title.text.toString()
            clipData.content = content.text.toString()

            realm.executeTransaction {
                it.copyToRealm(clipData)
            }
            Log.d("Status", "New clib data added")
        } catch (e: Exception) {
            Log.d("Status", "There are some errors")
        }
    }
}