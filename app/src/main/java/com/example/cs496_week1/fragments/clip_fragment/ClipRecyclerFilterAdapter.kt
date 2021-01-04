package com.example.cs496_week1.fragments.clip_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cs496_week1.R
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class ClipRecyclerFilterAdapter(data: OrderedRealmCollection<ClipRealmData>?, autoUpdate: Boolean) :
    RealmRecyclerViewAdapter<ClipRealmData, ClipRecyclerFilterAdapter.ViewHolder>(
        data, autoUpdate
    ) {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.title)
        val itemContent: TextView = itemView.findViewById(R.id.content)
        val itemIndex: TextView = itemView.findViewById(R.id.index)
        val goToUrl: LinearLayout = itemView.findViewById(R.id.url_card)
        val shareButton: ImageButton = itemView.findViewById(R.id.share)
        val editButton: ImageButton = itemView.findViewById(R.id.revice)
        val copyButton: ImageButton = itemView.findViewById(R.id.copy)
        val itemTag: TextView = itemView.findViewById(R.id.tag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_url_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val d = data?.get(position)
        holder.itemTitle.text = d!!.title
        holder.itemContent.text = d!!.content
        holder.itemIndex.text = (d!!.trick_id + 1).toString()
        holder.itemTag.text = d!!.tag
    }
}