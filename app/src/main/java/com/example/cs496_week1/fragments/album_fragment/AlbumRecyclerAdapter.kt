package com.example.cs496_week1.fragments.album_fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cs496_week1.R

class AlbumRecyclerAdapter(img: ArrayList<String>) :
    RecyclerView.Adapter<AlbumRecyclerAdapter.ViewHolder>() {
    var img = img

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImg: ImageView = itemView.findViewById(R.id.thumbImage)
        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                val context = itemView.context
                val intent = Intent(context, FullView::class.java).apply {
                    putExtra("position", position)
                    putExtra("uri_group", img)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_album_thumb_view, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        Glide.with(viewHolder.itemImg).load(img[i]).into(viewHolder.itemImg)
    }

    override fun getItemCount(): Int {
        return img.size
    }
}