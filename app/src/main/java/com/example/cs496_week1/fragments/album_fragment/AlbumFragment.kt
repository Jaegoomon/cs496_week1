package com.example.cs496_week1.fragments.album_fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cs496_week1.R
import java.io.IOException


class AlbumFragment : Fragment() {
    private val OPEN_GALLERY = 1
    private val idx = 20

    private val img = arrayListOf(
        R.drawable.img1, R.drawable.img2, R.drawable.img3,
        R.drawable.img4, R.drawable.img5, R.drawable.img6,
        R.drawable.img7, R.drawable.img8, R.drawable.img9,
        R.drawable.img10, R.drawable.img11, R.drawable.img12,
        R.drawable.img13, R.drawable.img14, R.drawable.img15,
        R.drawable.img16, R.drawable.img17, R.drawable.img18,
        R.drawable.img19, R.drawable.img20
    )

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapterImage.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_album, container, false)

        var addBtn = v.findViewById<ImageButton>(R.id.addBtn)
        addBtn.setOnClickListener { openGallary() }

        return v
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        itemView.findViewById<RecyclerView>(R.id.recycler_view).apply {
            // layoutManager = GridLayoutManager(activity, 3)
            layoutManager = LinearLayoutManager(activity)
            adapter = RecyclerAdapterImage(img)
        }
    }

    private fun openGallary() {
        var intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, OPEN_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == OPEN_GALLERY) {
                var photoUri: Uri? = data?.data
                val intent = Intent(activity, FullViewGallery::class.java)
                intent.putExtra("img", photoUri)
                startActivity(intent)
            }
        }
    }

}