package com.example.cs496_week1.fragments.clip_fragment

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.RequiresApi
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
    private var tagColor: Int? = null
    private var true_id: Int? = null
    private var trick_id: Int? = null


    @SuppressLint("Recycle")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_url)

        val colors = resources.obtainTypedArray(R.array.tag_color)
        val logo = resources.getDrawable(R.drawable.logo)

        realm = Realm.getDefaultInstance()
        button = findViewById(R.id.certification)
        url = findViewById(R.id.url)
        title = findViewById(R.id.title)
        content = findViewById(R.id.content)
        tag = findViewById(R.id.tag)
        val rawTagData = tag.text.toString()

        logo.setTint(Color.GRAY)
        tag.background = logo

        val intent = intent
        // Sharing case
        val receiveData = intent.getStringExtra("url")
        val receiveEditData = intent.getStringArrayListExtra("editData")
        if (receiveData != null) {
            url.setText(receiveData)
            Log.d("receive data", receiveData)
        }
        // Editing case
        if (receiveEditData != null) {
            title.setText(receiveEditData[0])
            content.setText(receiveEditData[1])
            url.setText(receiveEditData[2])
            tag.setText(receiveEditData[3])
            true_id = receiveEditData[4].toInt()
            trick_id = receiveEditData[5].toInt()
            logo.setTint(colors.getColor(receiveEditData[6].toInt(), 0))
            tag.background = logo
            tagColor = receiveEditData[6].toInt()
        }

        tag.setOnClickListener {
            AlertDialog.Builder(this@AddUrlActivity).setTitle("로고를 선택하세요.")
                .setItems(R.array.tag_name, DialogInterface.OnClickListener { dialog, which ->
                    val items = resources.getStringArray(R.array.tag_name)
                    logo.setTint(colors.getColor(which, 0))
                    tag.text = items[which]
                    tag.background = logo
                    tagColor = which
                }).show()
        }

        button.setOnClickListener {
            addClibDB(rawTagData)
        }
    }

    private fun addClibDB(rawTag: String) {
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
            var nextId: Int? = null
            var nextTrickId: Int? = null

            // Setting true id
            if (true_id != null) {
                nextId = true_id
            } else {
                val index: Number? = realm.where(ClipRealmData::class.java).max("id")
                nextId = if (index == null) {
                    0
                } else {
                    index.toInt() + 1
                }
            }
            // Setting trick id
            if (trick_id != null) {
                nextTrickId = trick_id
            } else {
                val trickIndex: Number? = realm.where(ClipRealmData::class.java).max("trick_id")
                nextTrickId = if (trickIndex == null) {
                    0
                } else {
                    trickIndex.toInt() + 1
                }
            }

            Log.d("Status", "set true and trick id")
            clipData.id = nextId!!
            clipData.trick_id = nextTrickId!!
            clipData.url = url.text.toString()
            clipData.title = title.text.toString()
            clipData.content = content.text.toString()
            clipData.tag = tag.text.toString()
            clipData.tag_color = tagColor!!

            realm.executeTransaction {
                it.copyToRealmOrUpdate(clipData)
            }
            finish()
        } catch (e: Exception) {
            Log.e("Status", "" + e)
        }
    }
}