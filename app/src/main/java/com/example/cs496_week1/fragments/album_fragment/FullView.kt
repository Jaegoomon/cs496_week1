package com.example.cs496_week1.fragments.album_fragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.example.cs496_week1.R
import com.github.chrisbanes.photoview.PhotoView

class FullView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_view)

        var fullImg: PhotoView = findViewById(R.id.fullView)
        val intent: Intent = getIntent()
        val img = intent.getIntExtra("IMG", 0)

        fullImg.setImageResource(img)
    }
}