package com.example.cs496_week1.fragments.clip_fragment

import android.content.DialogInterface
import android.content.Intent
import android.icu.text.CaseMap
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AlertDialog
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
    private lateinit var tag: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_url)

        realm = Realm.getDefaultInstance()
        button = findViewById(R.id.certification)
        url = findViewById(R.id.url)
        title = findViewById(R.id.title)
        content = findViewById(R.id.content)
        tag = findViewById(R.id.tag)
        val rawTagData = tag.text.toString()

        tag.setOnClickListener {
            AlertDialog.Builder(this@AddUrlActivity).setTitle("로고를 선택하세요.")
                .setItems(R.array.TAG, DialogInterface.OnClickListener { dialog, which ->
                    val items = getResources().getStringArray(R.array.TAG)
                    tag.setText(items[which])
                }).show()
        }

        button.setOnClickListener {
            addClibDB(rawTagData)
        }

        //
        val intent = getIntent()
        val receiveDAta = intent.getStringExtra("url")
        if (receiveDAta != null) {
            url.setText(receiveDAta)
            Log.d("receive data", receiveDAta)
            finish()
        }
        //
    }

    fun addClibDB(rawTag: String) {
        try {
            if (title.text.toString() == "") {
                Toast.makeText(this@AddUrlActivity, "제목을 추가하세요.", LENGTH_SHORT).show()
                Log.d("asdf", "You must write title")
                return
            }
            if (url.text.toString() == "") {
                Toast.makeText(this@AddUrlActivity, "주소를 추가하세요.", LENGTH_SHORT).show()
                Log.d("asdf", "You must write url")
                return
            }
            if (tag.text.toString() == rawTag) {
                Toast.makeText(this@AddUrlActivity, "태그를 선택해주세요.", LENGTH_SHORT).show()
                Log.d("asdf", "You must write url")
                return
            }
            val clipData = ClipRealmData()
            val index: Number? = realm.where(ClipRealmData::class.java).max("id")
            val nextId = if (index == null) {
                0
            } else {
                index.toInt() + 1
            }
            clipData.id = nextId
            clipData.url = url.text.toString()
            clipData.title = title.text.toString()
            clipData.content = content.text.toString()
            clipData.tag = tag.text.toString()

            realm.executeTransaction {
                it.copyToRealm(clipData)
            }
            finish()

            Log.d("Status", "New clib data added")
        } catch (e: Exception) {
            Log.d("Status", "There are some errors")
        }
    }
}