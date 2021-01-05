package com.example.cs496_week1.fragments.album_fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cs496_week1.MainActivity
import com.example.cs496_week1.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AlbumFragment(cursor: ArrayList<String>) : Fragment() {
    private val cursor = cursor
    private val REQUEST_TAKE_PHOTO = 1
    private lateinit var currentPhotoPath: String
    private lateinit var albumRecyclerAdapter: AlbumRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        Log.d("Status", "Fragment onViewCreated")
        itemView.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = GridLayoutManager(activity, 3)
            albumRecyclerAdapter = AlbumRecyclerAdapter(cursor)
            adapter = albumRecyclerAdapter
        }
    }

    private fun addButtonListener(button: FloatingActionButton) {
        button.setOnClickListener {
            (context as MainActivity).addPicture {
                addContentfunc(it)
            }
        }
    }

    fun addContentfunc(context: Context?) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(context!!.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (e: IOException) {
                    // Error occurred while creating the File
                    Log.e("Status", "" + e)
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        context!!,
                        "com.example.cs496_week1.fileprovider",
                        it
                    )
                    Log.d("Status", "photo Uri: " + photoURI)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                    Log.d("Status", "end of addContentfunc")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("Status", "Fragment onActivityResult")
        galleryAddPic()
        updateData()
    }

    private fun reStart() {
        activity?.finishAffinity()
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        System.exit(0)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        Log.d("Status", "storage dir: " + storageDir.toString())
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
            Log.d("Status", "absolute path: " + absolutePath)
        }
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)
            if(f.length() != 0L){
                mediaScanIntent.data = Uri.fromFile(f)
                context!!.sendBroadcast(mediaScanIntent)
            } else {
                f.delete()
            }
            Log.d("Status", "galleryAddPic: " + f.length())
        }
    }

    private fun updateData() {
        Log.d("Status", "update phto data")
        val photoData = ArrayList<String>()
        val photo = context!!.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
        )
        if (photo != null) {
            while (photo.moveToNext()) {
                val uri = photo.getString(photo.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                photoData.add(uri)
            }
            photo.close()
        }
        albumRecyclerAdapter.img = photoData
        albumRecyclerAdapter.notifyDataSetChanged()
    }
}