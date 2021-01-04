package com.example.cs496_week1.fragments.clip_fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cs496_week1.R
import io.realm.Realm

class ClipFilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clip_filter)
        val realm = Realm.getDefaultInstance()
        val rcView = findViewById<RecyclerView>(R.id.recycler_filter_view)

        val label = intent.getStringExtra("tag_label")
        if (label != null) {
            val labelList = realm.where(ClipRealmData::class.java).equalTo("tag", label).findAll()
            val rcAdapter = ClipRecyclerFilterAdapter(labelList, true)
            rcView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            rcView.adapter = rcAdapter
            Log.d("Status", "Im here")
        }
    }
}