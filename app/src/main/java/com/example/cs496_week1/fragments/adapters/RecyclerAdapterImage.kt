package com.example.cs496_week1.fragments.adapters

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.example.cs496_week1.R
import com.example.cs496_week1.fragments.FullView

class RecyclerAdapterImage(img: ArrayList<Int>) : RecyclerView.Adapter<RecyclerAdapterImage.ViewHolder>() {

    private val img = img

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImg: ImageView

        init {
            itemImg = itemView.findViewById(R.id.thumbImage)
            itemView.setOnClickListener {
                var position: Int = getAdapterPosition()
                val context = itemView.context
                val intent = Intent(context, FullView::class.java).apply {
                    putExtra("NUMBER", position)
                    putExtra("IMG", img[position])
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.thumb_view, viewGroup, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemImg.setImageResource(img[i])
    }

    override fun getItemCount(): Int {
        return img.size
    }
}