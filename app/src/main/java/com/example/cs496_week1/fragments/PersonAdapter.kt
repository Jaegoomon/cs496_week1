package com.example.cs496_week1.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.RecyclerView
import com.example.cs496_week1.R

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val name = arrayOf(
        "조재구",
        "조재구", "조재구", "조재구",
        "조재구", "9913dc4", "조재구",
        "조재구"
    )

    private val studentId = arrayOf(
        "20170633", "20170633",
        "20170633", "20170633",
        "20170633", "20170633",
        "20170633", "20170633"
    )

    private val number = arrayOf(
        "010-9833-1299",
        "010-9833-1299", "010-9833-1299", "010-9833-1299",
        "010-9833-1299", "010-9833-1299", "010-9833-1299",
        "010-9833-1299"
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: TextView
        var itemStudentId: TextView
        var itemNumber: TextView
        var itemPhoto: ImageView

        init {
            itemName = itemView.findViewById(R.id.name)
            itemStudentId = itemView.findViewById(R.id.student_id)
            itemNumber = itemView.findViewById(R.id.phone_number)
            itemPhoto = itemView.findViewById(R.id.photo)

            itemView.setOnClickListener {
                var position = adapterPosition
                val context = itemView.context
                Toast.makeText(context, "" + position + " was clicked", LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemName.text = name[position]
        holder.itemStudentId.text = studentId[position]
        holder.itemNumber.text = number[position]
        holder.itemPhoto.setImageResource(R.drawable.ic_baseline_person_24)
    }

    override fun getItemCount(): Int {
        return name.size
    }
}