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
        val add_button: FloatingActionButton = view.findViewById(R.id.add_button)
        rcAdapter = ContactRecyclerAdapter(context, cursor)
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = rcAdapter
        }
        addButtonListener(add_button)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        rcAdapter.notifyDataSetChanged()
        Log.d("sequence", "ContactFragment-onActivityResult123")
    }

    private fun addButtonListener(button: FloatingActionButton) {
        button.setOnClickListener {
            (activity as MainActivity).addContentfunc()
        }
    }

    private fun reStart() {
        activity?.finishAffinity()
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        System.exit(0)
    }
}