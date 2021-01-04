package com.example.cs496_week1.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cs496_week1.fragments.contact_fragment.ContactInfo
import com.example.cs496_week1.R

class ContactRecyclerAdapter(cursor: ArrayList<ContactInfo>) :
    RecyclerView.Adapter<ContactRecyclerAdapter.ViewHolder>() {

    private var cursor = cursor

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: TextView
        var itemNumber: TextView
        var itemPhoto: ImageView
        var itemCall: ImageButton = itemView.findViewById(R.id.call)
        var itemMessage: ImageButton = itemView.findViewById(R.id.message)

        init {
            itemName = itemView.findViewById(R.id.name)
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

            itemCall.setOnClickListener {
                var callIntent =
                    Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + cursor[adapterPosition].number))
                itemView.context.startActivity(callIntent)
            }
            itemMessage.setOnClickListener {
                var messageIntent = Intent(Intent.ACTION_VIEW)
                messageIntent.setType("vnd.android-dir/mms-sms")
                messageIntent.setData(Uri.parse("smsto:" + cursor[adapterPosition].number))
                messageIntent.putExtra("sms_body", "")
                itemView.context.startActivity(messageIntent)
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
        holder.itemNumber.text = cursor[position].number
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