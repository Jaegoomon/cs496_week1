package com.example.cs496_week1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
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
import io.realm.Realm

class MainActivity : AppCompatActivity() {
    private var realm: Realm? = null
    private val tabTextList = arrayListOf("연락처", "사진", "몰라")
    private val tabIconList = arrayListOf(
        R.drawable.ic_baseline_phone_24,
        R.drawable.ic_baseline_photo_24,
        R.drawable.ic_paper_clip
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            readData()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
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
            null,
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
                val photo =
                    result.getString(result.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                val obj = ContactInfo()
                obj.name =
                    result.getString(result.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                obj.number =
                    result.getString(result.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                obj.id =
                    result.getString(result.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID))
                obj.lookup =
                    result.getString(result.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY))
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