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
        ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
        ContactsContract.CommonDataKinds.Phone._ID,
        ContactsContract.Contacts.LOOKUP_KEY
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
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED || (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.READ_EXTERNAL_STORAGE

                ),
                111
            )
        } else {
            readData()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("sequence", "restart")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            readData()
    }


    private fun setUpTabs(cursor1: ArrayList<ContactInfo>, cursor2: ArrayList<String>) {
        val pager2: ViewPager2 = findViewById(R.id.viewPager2)
        val tabs: TabLayout = findViewById(R.id.tabs)

        pager2.adapter = ViewPageAdpater(this@MainActivity, cursor1, cursor2)
        TabLayoutMediator(tabs, pager2) { tab, position ->
            tab.setIcon(tabIconList[position])
            tab.text = tabTextList[position]
        }.attach()
    }

    private fun readData() {
        val contactData = arrayListOf<ContactInfo>()
        val photoData = arrayListOf<String>()
        val result = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            cols,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )
        val photo = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (result != null) {
            while (result.moveToNext()) {
                val name = result.getString(0)
                val number = result.getString(1)
                val photo = result.getString(2)
                val id = result.getString(3)
                val lookup = result.getString(4)

                val obj = ContactInfo()
                obj.name = name
                obj.number = number
                obj.id = id
                obj.lookup = lookup
                if (photo != null) {
                    obj.image = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(photo))
                }
                contactData.add(obj)
            }
            result.close()
        }

        if (photo != null) {
            while (photo.moveToNext()) {
                val uri = photo.getString(photo.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                photoData.add(uri)
            }
            photo.close()
        }
        setUpTabs(contactData, photoData)
    }
}