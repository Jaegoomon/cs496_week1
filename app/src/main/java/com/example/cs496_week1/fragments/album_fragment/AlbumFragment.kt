package com.example.cs496_week1.fragments.album_fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cs496_week1.MainActivity
import com.example.cs496_week1.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AlbumFragment(cursor: ArrayList<String>) : Fragment() {
    private val cursor = cursor
    val REQUEST_TAKE_PHOTO = 1
    lateinit var currentPhotoPath: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val add_button: ImageButton = itemView.findViewById(R.id.add_button)
        itemView.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = AlbumRecyclerAdapter(cursor)
        }
        addButtonListener(add_button)
    }

    private fun addButtonListener(button: ImageButton) {
        button.setOnClickListener {
            AlertDialog.Builder(activity)
                .setTitle("메뉴를 선택하세요")
                .setPositiveButton("새로고침") { dialogInterface: DialogInterface, i: Int -> reStart() }
                .setNegativeButton("사진 촬영") { dialogInterface: DialogInterface, i: Int -> addContentfunc() }
                .show()
        }
    }

    private fun addContentfunc() {
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
                        "com.example.android.fileprovider",
                        it
                    )
                    Log.d("Status", "" + photoURI)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        galleryAddPic()
    }

    private fun reStart() {
        activity?.finishAffinity()
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        System.exit(0)
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun galleryAddPic() {
        Log.d("Status", "galleryAddPic")
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            context!!.sendBroadcast(mediaScanIntent)
            Log.d("Status", "" + f)
        }
    }

}