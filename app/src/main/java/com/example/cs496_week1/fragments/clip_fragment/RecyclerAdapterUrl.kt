package com.example.cs496_week1.fragments.clip_fragment

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.cs496_week1.R
import io.realm.Realm
import java.lang.Exception

class RecyclerAdapterUrl(private val context: ClipFragment) :
    RecyclerView.Adapter<RecyclerAdapterUrl.ViewHolder>() {
    val realm = Realm.getDefaultInstance()

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
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_url_card_view, parent, false)
        view.findViewById<CardView>(R.id.card)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = realm.where(ClipRealmData::class.java).equalTo("id", position).findFirst()
        holder.itemTitle.setText(data!!.title)
        holder.itemContent.setText(data!!.content)
        holder.itemIndex.setText((data!!.id + 1).toString())
        holder.itemTag.setText(data!!.tag)

        holder.goToUrl.setOnClickListener {
            goToUrl(context.activity, data!!.url)
        }

        holder.goToUrl.setOnLongClickListener {
            val index = holder.itemIndex.text.toString().toInt()
            AlertDialog.Builder(context.activity).setTitle("삭제하시겠습니까?")
                .setNegativeButton("아니오") { _, _ -> }
                .setPositiveButton("예") { _, _ -> deleteDB(index - 1) }
                .show()
            Log.d("Status", "button was clicked long")
            true
        }

        holder.shareButton.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, data!!.url)
            sendIntent.type = "text/plain"

            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }

        holder.editButton.setOnClickListener {
            Log.d("Status", "Edit button was clicked")
            val intent = Intent(context.activity, AddUrlActivity::class.java)
            val editData = arrayListOf(
                data!!.title,
                data!!.content,
                data!!.url,
                data!!.tag,
                data!!.id.toString()
            )
            context.onClickButton(intent, editData)
        }

        holder.copyButton.setOnClickListener {
            val clipboardManager =
                context.activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("url", data!!.url)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(context.activity, "클립보드에 복사되었습니다.", LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return realm.where(ClipRealmData::class.java).findAll().size
    }

    fun deleteDB(index: Int) {
        try {
            val data = realm.where(ClipRealmData::class.java).equalTo("id", index).findFirst()
            realm.executeTransaction {
                data?.deleteFromRealm()
            }
            this.updateDB(index)
            this.notifyDataSetChanged()
            Log.d("Status", "Deletion completed")
        } catch (e: Exception) {
            Log.d("Status", "There are some errors.")
        }
    }

    fun updateDB(index: Int) {
        try {
            val start = index + 1
            val end = this.itemCount
            for (i in start..end) {
                val data = realm.where(ClipRealmData::class.java).equalTo("id", i).findFirst()
                realm?.executeTransaction {
                    data?.id = i - 1
                }
                Log.d("Status", "Update complete" + i)
            }
            Log.d("Status", "Update completed")
        } catch (e: Exception) {
            Log.d("Status", "There are some errors.")
        }
    }

    fun goToUrl(context: FragmentActivity?, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context?.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "URL이 올바르지 않습니다.", LENGTH_SHORT).show()
        }
    }
}