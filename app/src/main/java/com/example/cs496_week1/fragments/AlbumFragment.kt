package com.example.cs496_week1.fragments

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
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cs496_week1.R
import com.example.cs496_week1.fragments.adapters.RecyclerAdapterImage
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


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

        var addBtn = v.findViewById<Button>(R.id.addBtn)
        addBtn.setOnClickListener{ openGallary() }

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
        Log.d("TAG", "message1")

        if(resultCode == Activity.RESULT_OK){
            Log.d("TAG", "message2")
            if(requestCode == OPEN_GALLERY) {
                Log.d("TAG", "message3")

                var photoUri: Uri? = data?.data
                val bitmap = loadBitmapFromMediaStoreBy(photoUri!!)
                if (bitmap != null) {
                    saveBitmapAsFile(bitmap, "/")
                }
            }
        }
    }

    fun loadBitmapFromMediaStoreBy(photoUri: Uri): Bitmap? {
        var image: Bitmap? = null
        try {
            image = if (Build.VERSION.SDK_INT > 27) { // Api 버전별 이미지 처리
                val source: ImageDecoder.Source =
                    ImageDecoder.createSource(activity!!.contentResolver, photoUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(activity!!.contentResolver, photoUri)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }

    private fun saveBitmapAsFile(bitmap: Bitmap, filepath: String) {
        val file = File(filepath)
        var os: OutputStream? = null
        try {
            file.createNewFile()
            os = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
            os.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}