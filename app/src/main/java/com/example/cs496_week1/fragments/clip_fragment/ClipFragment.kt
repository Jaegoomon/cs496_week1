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


class ClipFragment : Fragment() {
    var moveX = 0f
    var moveY = 0f

    lateinit var rcAdapter: ClipRecyclerAdapterUrl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val add_button: ImageButton = view.findViewById(R.id.add_button)
        val recycler_view: RecyclerView = view.findViewById(R.id.recycler_view)
        rcAdapter = ClipRecyclerAdapterUrl(this)
        recycler_view.apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = rcAdapter
            addButtonListener(add_button)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("asdf", "Got it dsfdsdfsd")
        if (requestCode == 1111 || requestCode == 3333) {
            rcAdapter.notifyDataSetChanged()
        }
    }

    private fun addButtonListener(button: ImageButton) {
        button.setOnClickListener {
            val intent: Intent = Intent(activity, AddUrlActivity::class.java)
            startActivityForResult(intent, 1111)
        }

        button.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    moveX = v.x - event.rawX
                    moveY = v.y - event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    v.animate().x(event.rawX + moveX).y(event.rawY + moveY).setDuration(0).start()
                }
            }
            false
        }
    }

    fun onClickButton(intent: Intent, data: ArrayList<String>) {
        intent.putStringArrayListExtra("editData", data)
        startActivityForResult(intent, 3333)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.mainmenu, menu)
//    }
}