package com.example.cs496_week1.fragments

import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cs496_week1.fragments.contact_fragment.ContactInfo
import com.example.cs496_week1.R

class ContactRecyclerAdapter(cursor: ArrayList<ContactInfo>) :
    RecyclerView.Adapter<ContactRecyclerAdapter.ViewHolder>() {

    private var cursor = cursor

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
                var selectedContactUri: Uri? = null
                val position: Int = adapterPosition
                val editIntent = Intent(Intent.ACTION_VIEW).apply {
                    selectedContactUri = ContactsContract.Contacts.getLookupUri(
                        cursor[position].id.toLong(),
                        cursor[position].lookup
                    )
                }
                editIntent.setDataAndType(
                    selectedContactUri,
                    ContactsContract.Contacts.CONTENT_ITEM_TYPE
                )
                itemView.context.startActivity(editIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_contact_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemName.text = cursor[position].name
        holder.itemStudentId.text = cursor[position].number
        holder.itemNumber.text = cursor[position].name
        if (cursor[position].image != null) {
            holder.itemPhoto.setImageBitmap(cursor[position].image)
        } else {
            holder.itemPhoto.setImageResource(R.drawable.ic_baseline_person_24)
        }
    }

    override fun getItemCount(): Int {
        return cursor.size
    }
}