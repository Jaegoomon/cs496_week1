package com.example.cs496_week1.fragments.album_fragment

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.example.cs496_week1.R
import com.github.chrisbanes.photoview.PhotoView

class FullViewGallery : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_view_gallery)

        var fullImg: ImageView = findViewById(R.id.fullViewGallery)
        val intent: Intent = getIntent()
        val img = intent.getParcelableExtra<Uri>("img")

        fullImg.setImageURI(img)
    }
}