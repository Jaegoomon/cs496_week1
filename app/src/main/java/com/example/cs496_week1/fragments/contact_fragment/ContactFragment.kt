package com.example.cs496_week1.fragments.contact_fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cs496_week1.MainActivity
import com.example.cs496_week1.R
import com.example.cs496_week1.fragments.RecyclerAdapter

class ContactFragment(cursor: ArrayList<ContactInfo>) : Fragment() {
    var cursor = cursor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("sequence", "ContactFragment-onViewCreated")
        val recycler_view: RecyclerView = view.findViewById(R.id.recycler_view)
        val add_button: ImageButton = view.findViewById(R.id.add_button)
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = RecyclerAdapter(cursor)
        }
        addButtonListener(add_button)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("sequence", "ContactFragment-onActivityResult")
    }

    private fun addButtonListener(button: ImageButton) {
        button.setOnClickListener {
            AlertDialog.Builder(activity)
                .setTitle("설명")
                .setPositiveButton("새로고침") { dialogInterface: DialogInterface, i: Int -> reStart() }
                .setNegativeButton("연락처 추가") { dialogInterface: DialogInterface, i: Int -> addContentfunc() }
                .show()
        }
    }

    private fun addContentfunc() {
        val intent: Intent = Intent(activity, AddContactActivity::class.java)
        startActivityForResult(intent, 1)
    }

    private fun reStart() {
        activity?.finishAffinity()
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        System.exit(0)
    }
}