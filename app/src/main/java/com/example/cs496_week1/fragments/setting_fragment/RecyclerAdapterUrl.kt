package com.example.cs496_week1.fragments.setting_fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cs496_week1.R

class RecyclerAdapterUrl(private val context: Context, private val dataList: ArrayList<UrlInfo>) :
    RecyclerView.Adapter<RecyclerAdapterUrl.ViewHolder>() {

    var mPosition = 0
    fun getPosition(): Int{
        return mPosition
    }
    private fun setPosition(position: Int){
        mPosition = position
    }
    fun addItem(urlInfo: UrlInfo) {
        dataList.add(urlInfo)
        notifyDataSetChanged()
    }
    fun removeItem(position: Int) {
        if(position > 0) {
            dataList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView  = itemView.findViewById(R.id.title)
        var itemContent: TextView = itemView.findViewById(R.id.content)

        fun bind(urlInfo: UrlInfo) {
            itemTitle.text = urlInfo.title
            itemContent.text = urlInfo.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_url_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.itemView.setOnClickListener { view ->
            setPosition(position)
            Toast.makeText(view.context, "$position 아이템 클릭!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}