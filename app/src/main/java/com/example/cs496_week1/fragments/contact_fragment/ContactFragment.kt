package com.example.cs496_week1.fragments.contact_fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
            val intent: Intent = Intent(activity, AddContactActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }
}