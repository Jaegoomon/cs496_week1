package com.example.cs496_week1

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.cs496_week1.fragments.adapters.ViewPageAdpater
import com.example.cs496_week1.fragments.contact_fragment.ContactInfo
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    var cols = listOf<String>(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone.PHOTO_URI
    ).toTypedArray()
    var cursor = arrayListOf<ArrayList<String>>()

    private val tabTextList = arrayListOf("연락처", "사진", "몰라")
    private val tabIconList = arrayListOf(
        R.drawable.ic_baseline_phone_24,
        R.drawable.ic_baseline_photo_24,
        R.drawable.ic_baseline_question_answer_24
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        Log.d("sequence", "restart")
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                Array(1) { Manifest.permission.READ_CONTACTS },
                111
            )
        } else {
            readContact()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            readContact()
    }


    private fun setUpTabs(cursor: ArrayList<ContactInfo>) {
        val pager2: ViewPager2 = findViewById(R.id.viewPager2)
        val tabs: TabLayout = findViewById(R.id.tabs)

        pager2.adapter = ViewPageAdpater(this@MainActivity, cursor)
        TabLayoutMediator(tabs, pager2) { tab, position ->
            tab.setIcon(tabIconList[position])
            tab.text = tabTextList[position]
        }.attach()
    }

    private fun readContact() {
        val l = arrayListOf<ContactInfo>()
        val result = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            cols,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )
        if (result != null) {
            while (result.moveToNext()) {
                val name = result.getString(0)
                val number = result.getString(1)
                val obj = ContactInfo()
                val photo = result.getString(2)
                obj.name = name
                obj.number = number
                if (photo != null) {
                    obj.image = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(photo))
                }
                l.add(obj)
            }
        }
        setUpTabs(l)
    }
}