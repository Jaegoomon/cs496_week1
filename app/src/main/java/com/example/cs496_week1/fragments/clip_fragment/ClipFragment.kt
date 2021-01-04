package com.example.cs496_week1.fragments.clip_fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cs496_week1.R
import io.realm.Realm


class ClipFragment : Fragment() {

    lateinit var rcAdapter: ClipRecyclerFilterAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val add_button: ImageButton = view.findViewById(R.id.add_button)
        val recycler_view: RecyclerView = view.findViewById(R.id.recycler_view)
        rcAdapter = ClipRecyclerFilterAdapter(
            context!!,
            Realm.getDefaultInstance().where(ClipRealmData::class.java).findAll(),
            true
        )
        recycler_view.apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = rcAdapter
            addButtonListener(add_button)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("Status", "Got it dsfdsdfsd")
        if (requestCode == 1111 || requestCode == 3333) {
            rcAdapter.notifyDataSetChanged()
        }
    }

    private fun addButtonListener(button: ImageButton) {
        button.setOnClickListener {
            val intent: Intent = Intent(activity, AddUrlActivity::class.java)
            startActivityForResult(intent, 1111)
        }
    }
}