package com.example.cs496_week1.fragments.contact_fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cs496_week1.MainActivity
import com.example.cs496_week1.R
import com.example.cs496_week1.fragments.ContactRecyclerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContactFragment(cursor: ArrayList<ContactInfo>) : Fragment() {
    var cursor = cursor
    private lateinit var rcAdapter: ContactRecyclerAdapter

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
        rcAdapter = ContactRecyclerAdapter(context, cursor)
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = rcAdapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        rcAdapter.notifyDataSetChanged()
        Log.d("sequence", "ContactFragment-onActivityResult123")
    }
}