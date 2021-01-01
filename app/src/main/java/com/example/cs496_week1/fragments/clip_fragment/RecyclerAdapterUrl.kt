package com.example.cs496_week1.fragments.clip_fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cs496_week1.R
import io.realm.Realm

class RecyclerAdapterUrl(private val context: Context) :
    RecyclerView.Adapter<RecyclerAdapterUrl.ViewHolder>() {
    val realm = Realm.getDefaultInstance()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView = itemView.findViewById(R.id.title)
        var itemContent: TextView = itemView.findViewById(R.id.content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_url_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = realm.where(ClipData::class.java).equalTo("id", position).findFirst()
        holder.itemTitle.setText(data!!.title)
        holder.itemContent.setText(data!!.content)
    }

    override fun getItemCount(): Int {
        return realm.where(ClipData::class.java).findAll().size
    }
}