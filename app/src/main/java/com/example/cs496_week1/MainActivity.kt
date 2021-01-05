package com.example.cs496_week1

import android.Manifest
import android.content.Context
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
import com.example.cs496_week1.fragments.adapters.ViewPageAdapter
import com.example.cs496_week1.fragments.clip_fragment.AddUrlActivity
import com.example.cs496_week1.fragments.contact_fragment.ContactInfo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

const val GRANTED = PackageManager.PERMISSION_GRANTED

class MainActivity : AppCompatActivity() {
    private val tabTextList = arrayListOf("연락처", "사진", "링크")
    private val tabIconList = arrayListOf(
        R.drawable.ic_baseline_phone_24,
        R.drawable.ic_baseline_photo_24,
        R.drawable.ic_paper_clip
    )
    private val permissionList = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.SEND_SMS,
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    private val contactColumn = arrayOf(
        ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID,
        ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Status", "onCreate")
        readUrlData(intent)
        checkPermission()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("Status", "onNewIntent")
        readUrlData(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == GRANTED)
            readData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("Status", "onActivityResult")
        if (requestCode == 2222) {
            return finish()
        }
        if (requestCode == 101) {
            Thread.sleep(200)
            readData()
            return
        }
        Thread.sleep(200)
        readData(1)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    private fun setUpTabs(
        cursor1: ArrayList<ContactInfo>,
        cursor2: ArrayList<String>,
        defualtPage: Int = 0
    ) {
        val pager2: ViewPager2 = findViewById(R.id.viewPager2)
        val tabs: TabLayout = findViewById(R.id.tabs)
        val controller1: FloatingActionButton = findViewById(R.id.controller1)
        val controller2: FloatingActionButton = findViewById(R.id.controller2)
        val controller3: FloatingActionButton = findViewById(R.id.controller3)

        val adapter = ViewPageAdapter(this@MainActivity, cursor1, cursor2)
        pager2.adapter = adapter
        pager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        controller1.show()
                        controller2.hide()
                        controller3.hide()
                        Log.d("page listener", "page1")
                    }
                    1 -> {
                        controller1.hide()
                        controller2.show()
                        controller3.hide()
                        Log.d("page listener", "page2")
                    }
                    2 -> {
                        controller1.hide()
                        controller2.hide()
                        controller3.show()
                        Log.d("page listener", "page3")
                    }
                    else -> {
                        controller1.show()
                        controller2.hide()
                        controller3.hide()
                        Log.d("page listener", "page1")
                    }
                }
            }
        })

        controller1.setOnClickListener {
            Log.d("Clicked", "1 is clicked")
            addContentfunc()
        }
        controller2.setOnClickListener {
            Log.d("Clicked", "2 is clicked")
            addPicture {
                adapter.fragment2.addContentfunc(this@MainActivity)
            }
        }
        controller3.setOnClickListener {
            Log.d("Clicked", "3 is clicked")
            val intent: Intent = Intent(this@MainActivity, AddUrlActivity::class.java)
            startActivity(intent)
        }

        pager2.currentItem = defualtPage
        TabLayoutMediator(tabs, pager2) { tab, position ->
            tab.setIcon(tabIconList[position])
            tab.text = tabTextList[position]
        }.attach()
    }

    private fun readData(defualtPage: Int = 0) {
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
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
        )
        if (result != null) {
            while (result.moveToNext()) {
                val photo = result.getString(result.getColumnIndex(contactColumn[0]))
                val obj = ContactInfo()
                obj.name = result.getString(result.getColumnIndex(contactColumn[1]))
                obj.number = result.getString(result.getColumnIndex(contactColumn[2]))
                obj.id = result.getString(result.getColumnIndex(contactColumn[3]))
                obj.lookup = result.getString(result.getColumnIndex(contactColumn[4]))
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
        setUpTabs(contactData, photoData, defualtPage)
    }

    private fun readUrlData(intent: Intent?, defaultPage: Int = 0) {
        when (intent?.action) {
            Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    var data = intent.getStringExtra(Intent.EXTRA_TEXT)
                    if (data != null) {
                        Log.d("receive data", data)
                        val intent = Intent(this@MainActivity, AddUrlActivity::class.java)
                        intent.putExtra("url", data)
                        startActivityForResult(intent, 2222)
                    }
                }
            }
            else -> {
                Log.d("receive data", "None")
            }
        }
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                permissionList[0]
            ) != GRANTED || (ActivityCompat.checkSelfPermission(
                this,
                permissionList[1]
            ) != GRANTED) || (ActivityCompat.checkSelfPermission(
                this,
                permissionList[2]
            ) != GRANTED) || (ActivityCompat.checkSelfPermission(
                this,
                permissionList[3]
            ) != GRANTED) || (ActivityCompat.checkSelfPermission(
                this,
                permissionList[4]
            ) != GRANTED)
        ) {
            ActivityCompat.requestPermissions(this, permissionList, 111)
        } else {
            readData()
        }
    }

    fun addContentfunc() {
        with(Intent(Intent.ACTION_INSERT)) {
            this.setType(ContactsContract.RawContacts.CONTENT_TYPE)
            startActivityForResult(this, 101)
        }
    }

    fun addViewContentfunc(cursor: ArrayList<ContactInfo>, position: Int) {
        var selectedContactUri: Uri? = null
        val editIntent = Intent(Intent.ACTION_VIEW).apply {
            selectedContactUri = ContactsContract.Contacts.getLookupUri(
                cursor[position].id.toLong(),
                cursor[position].lookup
            )
        }
        editIntent.setDataAndType(selectedContactUri, ContactsContract.Contacts.CONTENT_ITEM_TYPE)
        startActivityForResult(editIntent, 101)
    }

    fun addPicture(f: (Context) -> Unit) {
        f(this)
    }
}