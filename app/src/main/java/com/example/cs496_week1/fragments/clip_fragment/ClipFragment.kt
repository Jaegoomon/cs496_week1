package com.example.cs496_week1.fragments.clip_fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cs496_week1.R
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class ClipFragment : Fragment() {
    lateinit var rcAdapter: RecyclerAdapterUrl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val add_button: ImageButton = view.findViewById(R.id.add_button)
        val recycler_view: RecyclerView = view.findViewById(R.id.recycler_view)
        rcAdapter = RecyclerAdapterUrl(view.context)
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = rcAdapter
            addButtonListener(add_button)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("asdf", "Got it")
        rcAdapter.notifyDataSetChanged()
    }

    private fun addButtonListener(button: ImageButton) {
        button.setOnClickListener {
            val intent: Intent = Intent(activity, AddUrlActivity::class.java)
            startActivityForResult(intent, 1111)
        }
    }
}